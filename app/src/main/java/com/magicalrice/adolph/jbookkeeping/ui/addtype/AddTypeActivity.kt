package com.magicalrice.adolph.jbookkeeping.ui.addtype

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.animation.AnimationUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.magicalrice.adolph.jbookkeeping.BookKeepingApplication
import com.magicalrice.adolph.jbookkeeping.Injection
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import com.magicalrice.adolph.jbookkeeping.databinding.ActivityAddTypeBinding
import com.magicalrice.adolph.jbookkeeping.datasource.BackupFailException
import com.magicalrice.adolph.jbookkeeping.utils.ToastUtils
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Adolph on 2018/7/12.
 */
@Route(path = RouterTable.Url.ITEM_ADD_TYPE, name = "添加类型")
class AddTypeActivity : BaseActivity() {
    private lateinit var mBinding: ActivityAddTypeBinding
    private lateinit var mViewModel:AddTypeViewModel
    private lateinit var mAdapter: TypeImgAdapter

    private var mType: Int = 0
    private var mRecordType: RecordType? = null

    override val layoutId: Int
        get() = R.layout.activity_add_type

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()
        val viewModelFactory = Injection.provideViewModelFactory()
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(AddTypeViewModel::class.java)

        initView()
        initData()
    }

    private fun initView() {
        mType = intent.getIntExtra(RouterTable.ExtraKey.KEY_TYPE,RecordType.TYPE_OUTLAY)
        mRecordType = intent.getSerializableExtra(RouterTable.ExtraKey.KEY_TYPE_BEAN) as RecordType?

        val prefix = if (mRecordType == null) getString(R.string.text_add) else getString(R.string.text_modify)
        val type = if (mType == RecordType.TYPE_OUTLAY) getString(R.string.text_outlay_type) else getString(R.string.text_income_type)

        mBinding.editTypeName.setText(mRecordType?.name)
        mBinding.editTypeName.setSelection(mBinding.editTypeName.text.length)

        mBinding.titleBar?.title = prefix + type
        mBinding.titleBar?.tvRight?.setText(R.string.text_save)
        mBinding.titleBar?.btnBack?.setOnClickListener { finish() }
        mBinding.titleBar?.tvRight?.setOnClickListener { saveType() }

        mBinding.rvType.layoutManager = GridLayoutManager(this, COLUMN)
        mAdapter = TypeImgAdapter(null)
        mBinding.rvType.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position -> checkItem(position) }
    }

    private fun checkItem(position: Int) {
        mAdapter.checkItem(position)
        val resId = resources.getIdentifier(mAdapter.currentItem!!.imgName, "mipmap", packageName)
        mBinding.ivType.setImageResource(resId)
    }

    private fun initData() {
        getAllTypeImg()
    }

    private fun getAllTypeImg() {
        mDisposable.add(mViewModel.getAllTypeImgBeans(mType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ typeImgBeans ->
                    mAdapter.setNewData(typeImgBeans)
                    if (mRecordType == null) {
                        checkItem(0)
                    } else {
                        for (i in typeImgBeans.indices) {
                            if (TextUtils.equals(mRecordType!!.imgName, typeImgBeans[i].imgName)) {
                                checkItem(i)
                                return@subscribe
                            }
                        }
                    }
                }) { throwable ->
                    ToastUtils.show(R.string.toast_type_img_fail)
                    Logger.e("类型图片获取失败", throwable)
                })
    }

    private fun saveType() {
        mBinding.titleBar?.tvRight?.isEnabled = false
        val text = mBinding.editTypeName.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(text)) {
            val animation = AnimationUtils.loadAnimation(BookKeepingApplication.instance, R.anim.shake)
            mBinding.editTypeName.startAnimation(animation)
            mBinding.titleBar?.tvRight?.isEnabled = true
            return
        }
        val bean = mAdapter.currentItem
        mDisposable.add(mViewModel.saveRecordType(mRecordType, mType, bean!!.imgName, text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.finish() }) { throwable ->
                    if (throwable is BackupFailException) {
                        ToastUtils.show(throwable.message)
                        Logger.e("备份失败（类型保存失败的时候）", throwable)
                        finish()
                    } else {
                        mBinding.titleBar?.tvRight?.isEnabled = true
                        val failTip = if (TextUtils.isEmpty(throwable.message)) getString(R.string.toast_type_save_fail) else throwable.message
                        ToastUtils.show(failTip)
                        Logger.e("类型保存失败", throwable)
                    }
                })
    }

    companion object {
        private const val COLUMN = 4
    }
}