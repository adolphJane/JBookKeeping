package com.magicalrice.adolph.jbookkeeping.ui.add

import com.magicalrice.adolph.jbookkeeping.BR
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseDataBindingAdapter
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType

/**
 * Created by Adolph on 2018/7/12.
 */
class TypeAdapter(data: List<RecordType>?) : BaseDataBindingAdapter<RecordType>(R.layout.item_type, data) {
    private var mCurrentCheckPosition: Int = 0
    private var mCurrentCheckId = -1
    private var mType: Int = 0

    /**
     * 获取当前选中的 item
     */
    val currentItem: RecordType?
        get() = getItem(mCurrentCheckPosition)

    override fun convert(helper: DataBindingViewHolder, item: RecordType) {
        val binding = helper.binding
        binding.setVariable(BR.recordType, item)
        binding.executePendingBindings()
    }

    /**
     * 筛选出支出和收入
     *
     * @param data 支出和收入总数据
     * @param type 类型 0：支出 1：收入
     * @see RecordType.TYPE_OUTLAY 支出
     *
     * @see RecordType.TYPE_INCOME 收入
     */
    fun setNewData(data: List<RecordType>?, type: Int) {
        mType = type
        if (data != null && data.isNotEmpty()) {
            val result = ArrayList<RecordType>()
            for (i in data.indices) {
                if (data[i].type == type) {
                    result.add(data[i])
                }
            }
            // 增加设置 item， type == -1 表示是设置 item
            val settingItem = RecordType(mContext.getString(R.string.text_setting), "type_item_setting", -1)
            result.add(settingItem)
            // 找出上次选中的 item
            var checkPosition = 0
            if (result[0].type != -1) {
                for (i in result.indices) {
                    if (result[i].id == mCurrentCheckId) {
                        checkPosition = i
                        break
                    }
                }
                super.setNewData(result)
                clickItem(checkPosition)
            } else {
                super.setNewData(result)
            }
        } else {
            super.setNewData(null)
        }
    }

    /**
     * 选中某一个 item，或点击设置 item
     *
     * @param position 选中 item 的索引
     */
    fun clickItem(position: Int) {
        // 点击设置 item
        val item = getItem(position)
        if (item != null && item.type == -1) {
            return
        }
        // 选中某一个 item
        var temp:RecordType?
        for (i in 0 until data.size) {
            temp = data[i]
            if (temp != null && temp.type != -1) {
                temp.isChecked = i == position
            }
        }
        mCurrentCheckPosition = position
        mCurrentCheckId = currentItem!!.id
        notifyDataSetChanged()
    }
}