package com.magicalrice.adolph.jbookkeeping.binding

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.magicalrice.adolph.jbookkeeping.ConfigManager
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import com.magicalrice.adolph.jbookkeeping.database.entity.SumMoneyBean
import com.magicalrice.adolph.jbookkeeping.utils.BigDecimalUtil
import com.magicalrice.adolph.jbookkeeping.utils.SizeUtils
import java.math.BigDecimal

/**
 * Created by Adolph on 2018/7/9.
 */
object BindAdapter {
    @JvmStatic
    @BindingAdapter("android:visibility")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("custom_margin_bottom")
    fun setMarginBottom(view: View,bottomMargin:Int) {
        val layoutParams = view.layoutParams
        val marginParams: ViewGroup.MarginLayoutParams
        marginParams = layoutParams as? ViewGroup.MarginLayoutParams ?: ViewGroup.MarginLayoutParams(layoutParams)
        marginParams.bottomMargin = SizeUtils.dp2px(bottomMargin.toFloat())
    }

    @JvmStatic
    @BindingAdapter("text_check_null")
    fun setText(textView: TextView,text:String) {
        textView.text = text
        textView.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("src_img_name")
    fun setImg(imageView: ImageView,imgName:String) {
        val resId = imageView.context.resources.getIdentifier(
                if (TextUtils.isEmpty(imgName)) "type_item_default" else imgName,
                "mimap",
                imageView.context.packageName)
        imageView.setImageResource(resId)
    }

    @JvmStatic
    @BindingAdapter("text_money")
    fun setMoneyText(textView: TextView,bigDecimal: BigDecimal) {
        textView.text = BigDecimalUtil.fen2Yuan(bigDecimal)
    }

    @JvmStatic
    @BindingAdapter("text_money_with_prefix")
    fun setMoneyTextWithPrefix(textView: TextView,bigDecimal: BigDecimal) {
        val text = textView.resources.getString(R.string.text_money_symbol) + BigDecimalUtil.fen2Yuan(bigDecimal)
        textView.text = text
    }

    @JvmStatic
    @BindingAdapter("text_income_or_budget")
    fun setTitleIncomeOrBudget(textView: TextView,list: List<SumMoneyBean>?) {
        if (ConfigManager.budget > 0) {
            textView.setText(R.string.text_month_remaining_budget)
        } else {
            textView.setText(R.string.text_month_income)
        }
    }

    @JvmStatic
    @BindingAdapter("text_month_outlay")
    fun setMonthOutlay(textView: TextView, sumMoneyBean: List<SumMoneyBean>?) {
        var outlay = "0"
        if (sumMoneyBean != null && sumMoneyBean.isNotEmpty()) {
            for ((type, sumMoney) in sumMoneyBean) {
                if (type == RecordType.TYPE_OUTLAY) {
                    outlay = BigDecimalUtil.fen2Yuan(sumMoney)
                }
            }
        }
        textView.text = outlay
    }

    @JvmStatic
    @BindingAdapter("text_month_income_or_budget")
    fun setMonthIncomeOrBudget(textView: TextView, sumMoneyBean: List<SumMoneyBean>?) {
        var outlay = BigDecimal(0)
        var inComeStr = "0"
        if (sumMoneyBean != null && sumMoneyBean.isNotEmpty()) {
            for ((type, sumMoney) in sumMoneyBean) {
                if (type == RecordType.TYPE_OUTLAY) {
                    outlay = sumMoney
                } else if (type == RecordType.TYPE_INCOME) {
                    inComeStr = BigDecimalUtil.fen2Yuan(sumMoney)
                }
            }
        }
        // 显示剩余预算或本月收入
        val budget = ConfigManager.budget
        if (budget > 0) {
            val budgetStr = BigDecimalUtil.fen2Yuan(BigDecimal(ConfigManager.budget).multiply(BigDecimal(100)).subtract(outlay))
            textView.text = budgetStr
        } else {
            textView.text = inComeStr
        }
    }

    @JvmStatic
    @BindingAdapter("text_statistics_outlay")
    fun setMonthStatisticsOutlay(textView: TextView, sumMoneyBean: List<SumMoneyBean>?) {
        val prefix = textView.context.getString(R.string.text_month_outlay_symbol)
        var outlay = prefix + "0"
        if (sumMoneyBean != null && sumMoneyBean.isNotEmpty()) {
            for ((type, sumMoney) in sumMoneyBean) {
                if (type == RecordType.TYPE_OUTLAY) {
                    outlay = prefix + BigDecimalUtil.fen2Yuan(sumMoney)
                }
            }
        }
        textView.text = outlay
    }

    @JvmStatic
    @BindingAdapter("text_statistics_income")
    fun setMonthStatisticsIncome(textView: TextView, sumMoneyBean: List<SumMoneyBean>?) {
        val prefix = textView.context.getString(R.string.text_month_income_symbol)
        var income = prefix + "0"
        if (sumMoneyBean != null && sumMoneyBean.isNotEmpty()) {
            for ((type, sumMoney) in sumMoneyBean) {
                if (type == RecordType.TYPE_INCOME) {
                    income = prefix + BigDecimalUtil.fen2Yuan(sumMoney)
                }
            }
        }
        textView.text = income
    }

    @JvmStatic
    @BindingAdapter("text_statistics_overage")
    fun setMonthStatisticsOverage(textView: TextView, sumMoneyBean: List<SumMoneyBean>?) {
        var outlayBd = BigDecimal(0)
        var incomeBd = BigDecimal(0)
        // 是否显示结余
        var isShowOverage = false
        if (sumMoneyBean != null && sumMoneyBean.isNotEmpty()) {
            for ((type, sumMoney) in sumMoneyBean) {
                if (type == RecordType.TYPE_OUTLAY) {
                    outlayBd = sumMoney
                } else if (type == RecordType.TYPE_INCOME) {
                    isShowOverage = sumMoney > BigDecimal(0)
                    incomeBd = sumMoney
                }
            }
        }
        if (isShowOverage) {
            textView.visibility = View.VISIBLE
            val prefix = textView.context.getString(R.string.text_month_overage)
            val overage = prefix + BigDecimalUtil.fen2Yuan(incomeBd.subtract(outlayBd))
            textView.text = overage
        } else {
            textView.visibility = View.GONE
        }
    }
}