package com.magicalrice.adolph.jbookkeeping.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Adolph on 2018/7/5.
 */
abstract class BaseActivity : AppCompatActivity() {
    private lateinit var dataBinding: ViewDataBinding
    protected val mDisposable = CompositeDisposable()

    @get:LayoutRes
    protected abstract val layoutId: Int

    //是否已经设置了沉浸式状态栏
    private var isSetupImmersive : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this,layoutId)

    }

    override fun onResume() {
        super.onResume()
        if (!isSetupImmersive) {
            setImmersiveStatus()
            isSetupImmersive = true
        }
    }

    protected abstract fun onInit(savedInstanceState: Bundle?)

    /**
     * 获取ViewDataBinding
     */
    protected fun <T : ViewDataBinding> getDataBinding(): T{
        return dataBinding as T
    }

    private fun setImmersiveStatus() {
        val views = setImmersiveView()
        if (views.isEmpty()) {
            return
        }
    }

    /**
     * 子类可以重写该方法设置沉浸式状态栏
     */
    protected fun setImmersiveView() : Array<View> {
        val rootView = dataBinding.root as ViewGroup
        return arrayOf(rootView.getChildAt(0))
    }
}