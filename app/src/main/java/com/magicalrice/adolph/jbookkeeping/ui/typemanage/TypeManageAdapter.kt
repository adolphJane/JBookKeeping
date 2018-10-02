package com.magicalrice.adolph.jbookkeeping.ui.typemanage

import com.magicalrice.adolph.jbookkeeping.BR
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseDataBindingAdapter
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import java.util.*

/**
 * 类型管理适配器
 *
 * @author bakumon https://bakumon.me
 * @date 2018/5/4
 */

class TypeManageAdapter(data: List<RecordType>?) : BaseDataBindingAdapter<RecordType>(R.layout.item_type_manage, data) {

    override fun convert(helper: BaseDataBindingAdapter.DataBindingViewHolder, item: RecordType) {
        val binding = helper.binding

        binding.setVariable(BR.recordType, item)
        val isLastItem = helper.adapterPosition == mData.size - 1
        // 单位是 dp
        binding.setVariable(BR.bottomMargin, if (isLastItem) 100 else 0)

        binding.executePendingBindings()
    }

    fun setNewData(data: List<RecordType>?, type: Int) {
        if (data != null && data.isNotEmpty()) {
            val result = ArrayList<RecordType>()
            for (i in data.indices) {
                if (data[i].type == type) {
                    result.add(data[i])
                }
            }
            super.setNewData(result)
        } else {
            super.setNewData(null)
        }
    }
}
