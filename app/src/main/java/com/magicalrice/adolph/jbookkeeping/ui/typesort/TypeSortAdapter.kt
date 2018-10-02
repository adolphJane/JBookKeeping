package com.magicalrice.adolph.jbookkeeping.ui.typesort

import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.magicalrice.adolph.jbookkeeping.BR
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseDaggableAdapter
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType

/**
 * 类型排序列表适配器
 *
 * @author bakumon https://bakumon.me
 * @date 2018/5/10
 */

class TypeSortAdapter(data: List<RecordType>?) : BaseDaggableAdapter<RecordType>(R.layout.item_type_sort, data), OnItemDragListener {

    init {
        setOnItemDragListener(this)
    }

    override fun convert(helper: BaseDaggableAdapter.DataBindingViewHolder, item: RecordType) {
        val binding = helper.binding
        binding.setVariable(BR.recordType, item)
        binding.executePendingBindings()
    }

    override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        (viewHolder as BaseViewHolder)
                .itemView.alpha = 0.8f
    }

    override fun onItemDragMoving(source: RecyclerView.ViewHolder, from: Int, target: RecyclerView.ViewHolder, to: Int) {

    }

    override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        (viewHolder as BaseViewHolder)
                .itemView.alpha = 1f
    }
}
