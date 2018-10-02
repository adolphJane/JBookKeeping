package com.magicalrice.adolph.jbookkeeping.ui.typemanage

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.magicalrice.adolph.jbookkeeping.Injection
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import com.magicalrice.adolph.jbookkeeping.databinding.ActivityTypeManageBinding
import com.magicalrice.adolph.jbookkeeping.datasource.BackupFailException
import com.magicalrice.adolph.jbookkeeping.utils.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 类型管理
 *
 * @author bakumon https://bakumon.me
 * @date 2018/5/3
 */
@Route(path = RouterTable.Url.ITEM_TYPE_MANAGE, name = "类型管理")
class TypeManageActivity : BaseActivity() {

    private lateinit var mBinding: ActivityTypeManageBinding
    private lateinit var mViewModel: TypeManageViewModel
    private lateinit var mAdapter: TypeManageAdapter

    private var mRecordTypes: List<RecordType>? = null

    private var mCurrentType: Int = 0

    override val layoutId: Int
        get() = R.layout.activity_type_manage

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()
        val viewModelFactory = Injection.provideViewModelFactory()
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TypeManageViewModel::class.java)

        initView()
        initData()
    }

    private fun initView() {
        mCurrentType = intent.getIntExtra(RouterTable.ExtraKey.KEY_TYPE, RecordType.TYPE_OUTLAY)
        mBinding.titleBar?.tvRight?.text = getString(R.string.text_button_sort)
        mBinding.titleBar?.btnBack?.setOnClickListener { finish() }
        mBinding.titleBar?.title = getString(R.string.text_title_type_manage)
        mBinding.titleBar?.tvRight?.setOnClickListener {
            ARouter.getInstance().build(RouterTable.Url.ITEM_TYPE_SORT).withInt(RouterTable.ExtraKey.KEY_TYPE,mCurrentType).navigation()
        }

        mBinding.rvType.layoutManager = LinearLayoutManager(this)
        mAdapter = TypeManageAdapter(null)
        mBinding.rvType.adapter = mAdapter

        mAdapter.setOnItemLongClickListener { adapter, view, position ->
            if (adapter.data.size > 1) {
                showDeleteDialog(mAdapter.data[position])
            } else {
                ToastUtils.show(R.string.toast_least_one_type)
            }
            true
        }

        mAdapter.setOnItemClickListener { _, _, position ->
            ARouter.getInstance().build(RouterTable.Url.ITEM_ADD_TYPE)
                    .withSerializable(RouterTable.ExtraKey.KEY_TYPE_BEAN,mAdapter.getItem(position))
                    .withInt(RouterTable.ExtraKey.KEY_TYPE,mCurrentType)
                    .navigation()
        }

        mBinding.typeChoice?.rgType?.setOnCheckedChangeListener { _, checkedId ->
            mCurrentType = if (checkedId == R.id.rb_outlay) RecordType.TYPE_OUTLAY else RecordType.TYPE_INCOME
            mAdapter.setNewData(mRecordTypes, mCurrentType)
            val visibility = if (mAdapter.data.size > 1) View.VISIBLE else View.INVISIBLE
            mBinding.titleBar?.tvRight?.visibility = visibility
        }

    }

    private fun showDeleteDialog(recordType: RecordType) {
        MaterialDialog.Builder(this)
                .title(getString(R.string.text_dialog_delete) + recordType.name!!)
                .content(R.string.text_delete_type_note)
                .positiveText(R.string.text_button_affirm_delete)
                .negativeText(R.string.text_button_cancel)
                .onPositive({ _, _ -> deleteType(recordType) })
                .show()
    }

    @Suppress("never used")
    fun addType(view: View) {
        ARouter.getInstance().build(RouterTable.Url.ITEM_ADD_TYPE)
                .withInt(RouterTable.ExtraKey.KEY_TYPE, mCurrentType)
                .navigation()
    }

    private fun deleteType(recordType: RecordType) {
        mDisposable.add(mViewModel.deleteRecordType(recordType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }
                ) { throwable ->
                    if (throwable is BackupFailException) {
                        ToastUtils.show(throwable.message)
                        Log.e(TAG, "备份失败（类型删除失败的时候）", throwable)
                    } else {
                        ToastUtils.show(R.string.toast_delete_fail)
                        Log.e(TAG, "类型删除失败", throwable)
                    }
                })
    }

    private fun initData() {
        mDisposable.add(mViewModel.allRecordTypes.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ recordTypes ->
                    mRecordTypes = recordTypes
                    val id = if (mCurrentType == RecordType.TYPE_OUTLAY) R.id.rb_outlay else R.id.rb_income
                    mBinding.typeChoice?.rgType?.clearCheck()
                    mBinding.typeChoice?.rgType?.check(id)
                }
                ) { throwable ->
                    ToastUtils.show(R.string.toast_get_types_fail)
                    Log.e(TAG, "获取类型数据失败", throwable)
                })
    }

    companion object {

        private val TAG = TypeManageActivity::class.java.simpleName
    }
}
