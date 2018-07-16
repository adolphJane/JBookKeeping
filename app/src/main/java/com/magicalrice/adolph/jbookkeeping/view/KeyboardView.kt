package com.magicalrice.adolph.jbookkeeping.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.databinding.DataBindingUtil
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.magicalrice.adolph.jbookkeeping.BookKeepingApplication
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.databinding.LayoutKeyboardBinding
import com.magicalrice.adolph.jbookkeeping.utils.SoftInputUtils

/**
 * 自定义键盘
 * Created by Adolph on 2018/7/13.
 */
class KeyboardView @JvmOverloads constructor(context: Context,attrs: AttributeSet? = null,defStyleAttr: Int = 0): LinearLayout(context,attrs, defStyleAttr) {
    private lateinit var mBinding: LayoutKeyboardBinding
    var mOnAffirmClickListener: ((String) -> Unit)? = null

    init {
        init(context)
    }

    fun setAffirmEnable(enable: Boolean) {
        mBinding.keyboardAffirm.isEnabled = enable
    }

    fun setText(text: String) {
        mBinding.editInput.setText(text)
        mBinding.editInput.setSelection(mBinding.editInput.text.length)
        SoftInputUtils.hideSoftInput(mBinding.editInput)
        if (!mBinding.editInput.isFocused) {
            mBinding.editInput.requestFocus()
        }
    }

    fun setEditTextFocus() {
        mBinding.editInput.requestFocus()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init(context: Context) {
        // 当前 activity 打开时不弹出软键盘
        val activity = context as Activity
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        orientation = LinearLayout.VERTICAL
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_keyboard,this,true)
        mBinding.editInput.requestFocus()
        mBinding.editInput.setOnTouchListener {_,_ ->
            SoftInputUtils.hideSoftInput(mBinding.editInput)
            mBinding.editInput.requestFocus()
            // 返回 true，拦截了默认的点击和长按操作，这是一个妥协的做法
            // 不再考虑多选粘贴的情况
            true
        }

        setInputTextViews(mBinding.keyboardNum0, mBinding.keyboardNum1,
                mBinding.keyboardNum2, mBinding.keyboardNum3,
                mBinding.keyboardNum4, mBinding.keyboardNum5,
                mBinding.keyboardNum6, mBinding.keyboardNum7,
                mBinding.keyboardNum8, mBinding.keyboardNum9,
                mBinding.keyboardNumPoint)
        setDeleteView(mBinding.keyboardDelete)

        mBinding.keyboardAffirm.setOnClickListener {
            val text = mBinding.editInput.text.toString()
            val isDigital = (!TextUtils.isEmpty(text)
                    && !TextUtils.equals("0", text)
                    && !TextUtils.equals("0.", text)
                    && !TextUtils.equals("0.0", text)
                    && !TextUtils.equals("0.00", text))
            if (!isDigital) {
                val animation = AnimationUtils.loadAnimation(BookKeepingApplication.instance, R.anim.shake)
                mBinding.editInput.startAnimation(animation)
            } else {
                mOnAffirmClickListener?.invoke(text)
            }
        }
    }

    /**
     * 设置键盘输入字符的textView，注意，textView点击后text将会输入到editText上
     */
    private fun setInputTextViews(vararg textViews: TextView) {
        val target = mBinding.editInput
        if (textViews.isEmpty()) {
            return
        }
        for (i in textViews.indices) {
            textViews[i].setOnClickListener {
                val sb = StringBuilder(target.text.toString().trim { it <= ' ' })
                val result = inputFilter(sb, textViews[i].text.toString())
                setText(result)
            }
        }
    }

    /**
     * 整数9位，小数2位
     */
    private fun inputFilter(sb: StringBuilder, text: String): String {
        if (sb.isEmpty()) {
            // 输入第一个字符
            if (TextUtils.equals(text, ".")) {
                sb.insert(0, "0.")
            } else {
                sb.insert(0, text)
            }
        } else if (sb.length == 1) {
            // 输入第二个字符
            if (sb.toString().startsWith("0")) {
                if (TextUtils.equals(".", text)) {
                    sb.insert(sb.length, ".")
                } else {
                    sb.replace(0, 1, text)
                }
            } else {
                sb.insert(sb.length, text)
            }
        } else if (sb.toString().contains(".")) {
            // 已经输入了小数点
            val length = sb.length
            val index = sb.indexOf(".")
            if (!TextUtils.equals(".", text)) {
                if (length - index < 3) {
                    sb.insert(sb.length, text)
                }
            }
        } else {
            // 整数
            if (TextUtils.equals(".", text)) {
                sb.insert(sb.length, text)
            } else {
                if (sb.length < MAX_INTEGER_NUMBER) {
                    sb.insert(sb.length, text)
                }
            }
        }
        return sb.toString()
    }

    /**
     * 设置删除键
     */
    private fun setDeleteView(deleteView: View) {
        val target = mBinding.editInput
        deleteView.setOnClickListener {
            val sb = StringBuilder(target.text.toString().trim { it <= ' ' })
            if (sb.isNotEmpty()) {
                sb.deleteCharAt(sb.length - 1)
                setText(sb.toString())
            }
        }
        deleteView.setOnLongClickListener {
            val sb = StringBuilder(target.text.toString().trim { it <= ' ' })
            if (sb.isNotEmpty()) {
                setText("")
            }
            false
        }
    }

    companion object {
        private const val MAX_INTEGER_NUMBER = 6
    }
}