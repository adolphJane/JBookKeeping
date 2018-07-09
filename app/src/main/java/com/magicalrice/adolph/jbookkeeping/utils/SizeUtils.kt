package com.magicalrice.adolph.jbookkeeping.utils

import com.magicalrice.adolph.jbookkeeping.BookKeepingApplication

/**
 * Created by Adolph on 2018/7/9.
 */
object SizeUtils {
    fun dp2px(dpValue: Float): Int {
        val scale = BookKeepingApplication.instance.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}