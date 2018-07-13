package com.magicalrice.adolph.jbookkeeping.ui.statistic.reports

import com.magicalrice.adolph.jbookkeeping.BR
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseDataBindingAdapter
import com.magicalrice.adolph.jbookkeeping.database.entity.TypeSumMoneyBean

/**
 * Created by Adolph on 2018/7/13.
 */
class ReportAdapter(data: List<TypeSumMoneyBean>?) : BaseDataBindingAdapter<TypeSumMoneyBean>(R.layout.item_report,data) {

    override fun convert(helper: DataBindingViewHolder, item: TypeSumMoneyBean) {
        val binding = helper.binding
        binding.setVariable(BR.typeSumMoney,item)
        binding.executePendingBindings()
    }
}