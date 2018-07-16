package com.magicalrice.adolph.jbookkeeping.ui.typesort

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.magicalrice.adolph.jbookkeeping.Injection
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import com.magicalrice.adolph.jbookkeeping.databinding.ActivityTypeSortBinding
import com.magicalrice.adolph.jbookkeeping.datasource.BackupFailException
import com.magicalrice.adolph.jbookkeeping.utils.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 类型排序
 *
 * @author bakumon https://bakumon.me
 * @date 2018/5/10
 */
@Route(path = RouterTable.Url.ITEM_TYPE_SORT, name = "类型排序")
class TypeSortActivity : BaseActivity() {

    private lateinit var mBinding: ActivityTypeSortBinding
    private lateinit var mViewModel: TypeSortViewModel
    private lateinit var mAdapter: TypeSortAdapter
    private var mType: Int = 0

    override val layoutId: Int
        get() = R.layout.activity_type_sort

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()
        val viewModelFactory = Injection.provideViewModelFactory()
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TypeSortViewModel::class.java)

        initView()
        initData()
    }

    private fun initView() {
        mType = intent.getIntExtra(RouterTable.ExtraKey.KEY_TYPE, RecordType.TYPE_OUTLAY)

        mBinding.titleBar?.btnBack?.setOnClickListener { finish() }
        mBinding.titleBar?.title = getString(R.string.text_title_drag_sort)
        mBinding.titleBar?.tvRight?.text = getString(R.string.text_done)
        mBinding.titleBar?.tvRight?.setOnClickListener { sortRecordTypes() }

        mBinding.rvType.layoutManager = GridLayoutManager(this, COLUMN)
        mAdapter = TypeSortAdapter(null)
        mBinding.rvType.adapter = mAdapter

        val itemDragAndSwipeCallback = ItemDragAndSwipeCallback(mAdapter)
        val itemTouchHelper = ItemTouchHelper(itemDragAndSwipeCallback)
        itemTouchHelper.attachToRecyclerView(mBinding.rvType)

        // open drag
        mAdapter.enableDragItem(itemTouchHelper)
    }

    private fun sortRecordTypes() {
        mBinding.titleBar?.tvRight?.isEnabled = false
        mDisposable.add(mViewModel.sortRecordTypes(mAdapter.data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.finish() }) { throwable ->
                    if (throwable is BackupFailException) {
                        ToastUtils.show(throwable.message)
                        Log.e(TAG, "备份失败（类型排序失败的时候）", throwable)
                        finish()
                    } else {
                        mBinding.titleBar?.tvRight?.isEnabled = true
                        ToastUtils.show(R.string.toast_sort_fail)
                        Log.e(TAG, "类型排序失败", throwable)
                    }
                })
    }

    private fun initData() {
        mDisposable.add(mViewModel.getRecordTypes(mType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ recordTypes -> mAdapter.setNewData(recordTypes) }
                ) { throwable ->
                    ToastUtils.show(R.string.toast_get_types_fail)
                    Log.e(TAG, "获取类型数据失败", throwable)
                })
    }

    companion object {

        private val TAG = TypeSortActivity::class.java.simpleName

        private const val COLUMN = 4
    }
}
