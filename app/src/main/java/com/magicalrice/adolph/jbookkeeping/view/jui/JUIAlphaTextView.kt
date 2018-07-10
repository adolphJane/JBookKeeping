package com.magicalrice.adolph.jbookkeeping.view.jui

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.widget.AutoCompleteTextView
import com.magicalrice.adolph.jbookkeeping.view.jui.util.JUIAlphaViewHelper

/**
 * Created by Adolph on 2018/7/10.
 */
class JUIAlphaTextView : AppCompatTextView {
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
        alphaViewHelper?.onPressedChanged(this, pressed)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        alphaViewHelper?.onEnabledChanged(this, enabled)
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    fun setChangeAlphaWhenPress(changeAlphaWhenPress: Boolean) {
        alphaViewHelper?.setChangeAlphaWhenPress(changeAlphaWhenPress)
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    fun setChangeAlphaWhenDisable(changeAlphaWhenDisable: Boolean) {
        alphaViewHelper?.setChangeAlphaWhenDisable(changeAlphaWhenDisable)
    }
}