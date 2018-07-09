package com.magicalrice.adolph.jbookkeeping.view.util

import android.view.View
import java.lang.ref.WeakReference

/**
 * Created by Adolph on 2018/7/9.
 */
class JUIAlphaViewHelper {
    private var mTarget: WeakReference<View>? = null
    //设置是否要在press时改变透明度
    private var mChangeAlphaWhenPress = true
    //设置是否要在disabled时改变透明度
    private var mChangeAlphaWhenDisable = true
    private val mNormalAlpha = 1f
    private var mPressedAlpha = .5f
    private var mDisabledAlpha = .5f

    constructor(target: View) {
        mTarget = WeakReference(target)
    }

    constructor(target: View,pressedAlpha: Float,disabledAlpha: Float) {
        mTarget = WeakReference(target)
        mPressedAlpha = pressedAlpha
        mDisabledAlpha = disabledAlpha
    }

    fun onPressedChanged(current: View,pressed: Boolean) {
        val target = mTarget!!.get() ?: return
        if (current.isEnabled) {
            target.alpha = if (mChangeAlphaWhenPress && pressed && current.isClickable) mPressedAlpha else mNormalAlpha
        } else {
            if (mChangeAlphaWhenDisable) {
                target.alpha = mDisabledAlpha
            }
        }
    }

    fun onEnabledChanged(current: View,enabled:Boolean) {
        val target = mTarget!!.get() ?: return
        val alphaForIsEnable: Float
        if (mChangeAlphaWhenDisable) {
            alphaForIsEnable = if (enabled) mNormalAlpha else mDisabledAlpha
        } else {
            alphaForIsEnable = mNormalAlpha
        }
        if (current !== target && target.isEnabled != enabled) {
            target.isEnabled = enabled
        }
        target.alpha = alphaForIsEnable
    }

    /**
     * 设置是否要在press时改变透明度
     */
    fun setChangeAlphaWhenPress(changeAlphaWhenPress: Boolean) {
        mChangeAlphaWhenPress = changeAlphaWhenPress
    }

    /**
     * 设置是否要在disable时改变透明度
     */
    fun setChangeAlphaWhenDisable(changeAlphaWhenDisable: Boolean) {
        mChangeAlphaWhenDisable = changeAlphaWhenDisable
        val target = mTarget!!.get()
        if (target != null) {
            onEnabledChanged(target,target.isEnabled)
        }
    }
}