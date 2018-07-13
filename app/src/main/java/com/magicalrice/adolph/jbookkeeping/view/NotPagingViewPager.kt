package com.magicalrice.adolph.jbookkeeping.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 可禁止滑动的 ViewPager
 * Created by Adolph on 2018/7/13.
 */
class NotPagingViewPager(context: Context,attrs: AttributeSet) : ViewPager(context,attrs) {
    private var pagingEnd: Boolean = false

    init {
        this.pagingEnd = false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if (this.pagingEnd) {
            super.onTouchEvent(ev)
        } else false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (this.pagingEnd) {
            super.onInterceptTouchEvent(ev)
        } else false
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.pagingEnd = enabled
    }
}