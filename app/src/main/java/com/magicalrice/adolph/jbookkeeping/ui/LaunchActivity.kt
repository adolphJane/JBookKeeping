package com.magicalrice.adolph.jbookkeeping.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.RouterTable

/**
 * 启动Activity
 * Created by Adolph on 2018/7/5.
 */
class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        ARouter.getInstance().build(RouterTable.Url.ITEM_HOME).navigation(this, object : NavCallback() {
            override fun onArrival(postcard: Postcard?) {
                this@LaunchActivity.finish()
            }
        })
    }
}