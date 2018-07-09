package com.magicalrice.adolph.jbookkeeping.utils

import android.support.annotation.StringRes
import android.widget.Toast
import com.magicalrice.adolph.jbookkeeping.BookKeepingApplication

/**
 * Created by Adolph on 2018/7/9.
 */
object ToastUtils {
    fun show(@StringRes resId: Int) {
        Toast.makeText(BookKeepingApplication.instance, resId, Toast.LENGTH_SHORT).show()
    }

    fun show(msg: String?) {
        Toast.makeText(BookKeepingApplication.instance, msg, Toast.LENGTH_SHORT).show()
    }
}