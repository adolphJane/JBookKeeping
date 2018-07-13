package com.magicalrice.adolph.jbookkeeping.ui.add

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.magicalrice.adolph.jbookkeeping.Injection
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.database.entity.Record
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordWithType
import com.magicalrice.adolph.jbookkeeping.databinding.ActivityAddRecordBinding
import com.magicalrice.adolph.jbookkeeping.datasource.BackupFailException
import com.magicalrice.adolph.jbookkeeping.utils.BigDecimalUtil
import com.magicalrice.adolph.jbookkeeping.utils.DateUtils
import com.magicalrice.adolph.jbookkeeping.utils.SoftInputUtils
import com.magicalrice.adolph.jbookkeeping.utils.ToastUtils
import com.orhanobut.logger.Logger
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Adolph on 2018/7/11.
 */
@Route(path = RouterTable.Url.ITEM_ADD_RECORD, name = "添加记录")
class AddRecordActivity : BaseActivity(){
    private lateinit var mBinding: ActivityAddRecordBinding
    private lateinit var mViewModel: AddRecordViewModel

    private var mCurrentChooseDate: Date? = DateUtils.getTodayDate()
    private val mCurrentChooseCalendar = Calendar.getInstance()
    private var mCurrentType: Int = 0
    private var mRecord: RecordWithType? = null

    //连续记账
    private var mIsSuccessive: Boolean = false

    override val layoutId: Int
        get() = R.layout.activity_add_record

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()
        val viewModelFactory = Injection.provideViewModelFactory()
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(AddRecordViewModel::class.java)

        initView()
        initData()
    }

    private fun initData() {
        getAllRecordTypes()
    }

    private fun initView() {
        mRecord = intent.getSerializableExtra(RouterTable.ExtraKey.KEY_RECORD_BEAN) as RecordWithType?
        mIsSuccessive = intent.getBooleanExtra(RouterTable.ExtraKey.KEY_IS_SUCCESSIVE, false)

        mBinding.titleBar?.btnBack?.setBackgroundResource(R.drawable.ic_close)
        mBinding.titleBar?.btnBack?.setOnClickListener { finish() }

        mBinding.editRemark.setOnEditorActionListener { _, _, _ ->
            SoftInputUtils.hideSoftInput(mBinding.typePageOutlay)
            mBinding.keyboard.setEditTextFocus()
            false
        }

        if (mRecord == null) {
            mCurrentType = RecordType.TYPE_OUTLAY
            mBinding.titleBar?.title = getString(if (mIsSuccessive) R.string.text_add_record_successive else R.string.text_add_record)
        } else {
            mCurrentType = mRecord!!.mRecordTypes!![0].type
            mBinding.titleBar?.title = getString(R.string.text_modify_record)
            mBinding.editRemark.setText(mRecord!!.remark)
            mBinding.keyboard.setText(BigDecimalUtil.fen2Yuan(mRecord!!.money))
            mCurrentChooseDate = mRecord!!.time
            mCurrentChooseCalendar.time = mCurrentChooseDate
            mBinding.qmTvDate.text = DateUtils.getWordTime(mCurrentChooseDate!!)
        }

        mBinding.keyboard.mOnAffirmClickListener = {
            if (mRecord == null) {
                insertRecord(it)
            } else {
                modifyRecord(it)
            }
        }

        mBinding.qmTvDate.setOnClickListener {
            val dpd = DatePickerDialog.newInstance(
                    { _, year, monthOfYear, dayOfMonth ->
                        mCurrentChooseDate = DateUtils.getDate(year, monthOfYear + 1, dayOfMonth)
                        mCurrentChooseCalendar.time = mCurrentChooseDate
                        mBinding.qmTvDate.text = DateUtils.getWordTime(mCurrentChooseDate!!)
                    }, mCurrentChooseCalendar)
            dpd.maxDate = Calendar.getInstance()
            dpd.show(fragmentManager, TAG_PICKER_DIALOG)
        }
        mBinding.typeChoice?.rgType?.setOnCheckedChangeListener { _, checkedId ->

            if (checkedId == R.id.rb_outlay) {
                mCurrentType = RecordType.TYPE_OUTLAY
                mBinding.typePageOutlay.visibility = View.VISIBLE
                mBinding.typePageIncome.visibility = View.GONE
            } else {
                mCurrentType = RecordType.TYPE_INCOME
                mBinding.typePageOutlay.visibility = View.GONE
                mBinding.typePageIncome.visibility = View.VISIBLE
            }

        }
    }

    private fun insertRecord(text: String) {
        // 防止重复提交
        mBinding.keyboard.setAffirmEnable(false)
        val record = Record()
        record.money = BigDecimalUtil.yuan2FenBD(text)
        record.remark = mBinding.editRemark.text.toString().trim { it <= ' ' }
        record.time = mCurrentChooseDate
        record.createTime = Date()
        record.recordTypeId = if (mCurrentType == RecordType.TYPE_OUTLAY)
            mBinding.typePageOutlay.currentItem!!.id
        else
            mBinding.typePageIncome.currentItem!!.id

        mDisposable.add(mViewModel.insertRecord(record)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.insertRecordDone() }
                ) { throwable ->
                    if (throwable is BackupFailException) {
                        ToastUtils.show(throwable.message)
                        Logger.e("备份失败（新增记录失败的时候）", throwable)
                        insertRecordDone()
                    } else {
                        Logger.e("新增记录失败", throwable)
                        mBinding.keyboard.setAffirmEnable(true)
                        ToastUtils.show(R.string.toast_add_record_fail)
                    }
                })
    }

    /**
     * 新增记账记录完成
     */
    private fun insertRecordDone() {
        if (mIsSuccessive) {
            // 继续记账，清空输入
            mBinding.keyboard.setText("")
            mBinding.editRemark.setText("")
            mBinding.keyboard.setAffirmEnable(true)
            ToastUtils.show(R.string.toast_success_record)
        } else {
            finish()
        }
    }

    private fun modifyRecord(text: String) {
        // 防止重复提交
        mBinding.keyboard.setAffirmEnable(false)
        mRecord!!.money = BigDecimalUtil.yuan2FenBD(text)
        mRecord!!.remark = mBinding.editRemark.text.toString().trim { it <= ' ' }
        mRecord!!.time = mCurrentChooseDate
        mRecord!!.recordTypeId = if (mCurrentType == RecordType.TYPE_OUTLAY)
            mBinding.typePageOutlay.currentItem!!.id
        else
            mBinding.typePageIncome.currentItem!!.id

        mDisposable.add(mViewModel.updateRecord(mRecord!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.finish() }
                ) { throwable ->
                    if (throwable is BackupFailException) {
                        ToastUtils.show(throwable.message)
                        Logger.e("备份失败（记录修改失败的时候）", throwable)
                        finish()
                    } else {
                        Logger.e("记录修改失败", throwable)
                        mBinding.keyboard.setAffirmEnable(true)
                        ToastUtils.show(R.string.toast_modify_record_fail)
                    }
                })
    }

    private fun getAllRecordTypes() {
        mDisposable.add(mViewModel.allRecordTypes
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ recordTypes ->
                    mBinding.typePageOutlay.setNewData(recordTypes, RecordType.TYPE_OUTLAY)
                    mBinding.typePageIncome.setNewData(recordTypes, RecordType.TYPE_INCOME)

                    if (mCurrentType == RecordType.TYPE_OUTLAY) {
                        mBinding.typeChoice?.rgType?.check(R.id.rb_outlay)
                        mBinding.typePageOutlay.initCheckItem(mRecord)
                    } else {
                        mBinding.typeChoice?.rgType?.check(R.id.rb_income)
                        mBinding.typePageIncome.initCheckItem(mRecord)
                    }

                }) { throwable ->
                    ToastUtils.show(R.string.toast_get_types_fail)
                    Logger.e("获取类型数据失败", throwable)
                })
    }

    companion object {
        private const val TAG_PICKER_DIALOG = "Datepickerdialog"
    }
}