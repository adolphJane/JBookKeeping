package com.magicalrice.adolph.jbookkeeping.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable

/**
 * BaseFragment
 * Created by Adolph on 2018/7/6.
 */

abstract class BaseFragment : Fragment() {

    //标志位 判断数据是否初始化
    private var isInitData = false
    //标志位 判断fragment是否可见
    private var isVisibleToUser = false
    //标志位 判断view已经加载完成 避免空指针操作
    private var isPrepareView = false
    private lateinit var dataBinding: ViewDataBinding
    protected val mDisposable = CompositeDisposable()

    //子类必须实现，用于创建 view
    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        val rootView = dataBinding.root
        onInit(savedInstanceState)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPrepareView = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }

    /**
     * 懒加载
     */
    private fun initData() {
        if (!isInitData && isVisibleToUser && isPrepareView) {
            isInitData = true
            lazyInitData()
        }
    }

    protected fun inflate(@LayoutRes resource: Int) : View {
        return layoutInflater.inflate(resource,null,false)
    }

    /**
     * 开始的方法
     * @param savedInstanceState 保存的Bundle
     */
    protected abstract fun onInit(savedInstanceState: Bundle?)

    protected abstract fun lazyInitData()

    /**
     * 获取 ViewDataBinding
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <T : ViewDataBinding> getDataBinding(): T {
        return dataBinding as T
    }
}