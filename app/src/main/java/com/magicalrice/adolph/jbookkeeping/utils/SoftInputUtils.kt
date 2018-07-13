package com.magicalrice.adolph.jbookkeeping.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Adolph on 2018/7/13.
 */
object SoftInputUtils {
    /**
     * 隐藏软键盘
     *
     * @param view 当前屏幕中任意一个 view
     */
    fun hideSoftInput(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
}