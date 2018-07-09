package com.magicalrice.adolph.jbookkeeping.base

import android.content.res.Configuration
import android.content.res.Resources
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
        onInit(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (!isSetupImmersive) {
            setImmersiveStatus()
            isSetupImmersive = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }

    protected abstract fun onInit(savedInstanceState: Bundle?)

    protected fun inflate(@LayoutRes resource: Int): View {
        return layoutInflater.inflate(resource,null,false)
    }

    /**
     * 获取ViewDataBinding
     */
    @Suppress("UNCHECKED_CAST")
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

    override fun getResources(): Resources {
        //固定字体大小，不随系统字体大小改变
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config,res.displayMetrics)
        return res
    }
}