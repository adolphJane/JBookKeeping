package com.magicalrice.adolph.jbookkeeping

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by Adolph on 2018/7/5.
 */
class BookKeepingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    companion object {
        lateinit var instance: BookKeepingApplication
    }
}