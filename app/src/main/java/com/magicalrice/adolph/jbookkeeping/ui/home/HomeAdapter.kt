package com.magicalrice.adolph.jbookkeeping.ui.home

import com.magicalrice.adolph.jbookkeeping.BR
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseDataBindingAdapter
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordWithType
import com.magicalrice.adolph.jbookkeeping.utils.DateUtils

/**
 * Created by Adolph on 2018/7/6.
 */
class HomeAdapter(data: List<RecordWithType>?) : BaseDataBindingAdapter<RecordWithType>(R.layout.item_ry_home, data) {
    override fun convert(helper: BaseDataBindingAdapter.DataBindingViewHolder, item: RecordWithType) {
        val binding = helper.binding
        helper.addOnLongClickListener(R.id.ll_item_click)
        binding.setVariable(BR.recordWithType, item)
        val isDataShow = helper.adapterPosition == 0 || !DateUtils.isSameDay(item.time!!, data[helper.adapterPosition - 1].time!!)
        binding.setVariable(BR.isDataShow, isDataShow)
        binding.executePendingBindings()
    }
}