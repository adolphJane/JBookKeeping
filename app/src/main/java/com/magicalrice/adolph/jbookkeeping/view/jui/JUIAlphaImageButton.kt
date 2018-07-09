package com.magicalrice.adolph.jbookkeeping.view.jui

import android.content.Context
import android.support.v7.widget.AppCompatImageButton
import android.util.AttributeSet
import com.magicalrice.adolph.jbookkeeping.view.util.JUIAlphaViewHelper

/**
 * Created by Adolph on 2018/7/9.
 */
class JUIAlphaImageButton : AppCompatImageButton {
    private var mAlphaViewHelper: JUIAlphaViewHelper? = null
    private val alphaViewHelper: JUIAlphaViewHelper?
    get() {
        if (mAlphaViewHelper == null) {
            mAlphaViewHelper = JUIAlphaViewHelper(this)
        }
        return mAlphaViewHelper
    }
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        alphaViewHelper?.onPressedChanged(this,pressed)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        alphaViewHelper?.onEnabledChanged(this,enabled)
    }

    fun setChangeAlphaWhenPress(changeAlphaWhenPress: Boolean) {
        alphaViewHelper?.setChangeAlphaWhenPress(changeAlphaWhenPress)
    }

    fun setChangeAlphaWhenDisable(changeAlphaWhenDisable: Boolean) {
        alphaViewHelper?.setChangeAlphaWhenDisable(changeAlphaWhenDisable)
    }
}