package com.magicalrice.adolph.jbookkeeping.ui.addtype

import com.magicalrice.adolph.jbookkeeping.BR
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseDataBindingAdapter

/**
 * 类型图片适配器
 * Created by Adolph on 2018/7/12.
 */
class TypeImgAdapter(data: List<TypeImgBean>?) : BaseDataBindingAdapter<TypeImgBean>(R.layout.item_type_img, data) {
    private var mCurrentCheckPosition: Int = 0

    /**
     * 获取当前选中的 item
     */
    val currentItem: TypeImgBean?
        get() = getItem(mCurrentCheckPosition)

    override fun convert(helper: DataBindingViewHolder, item: TypeImgBean) {
        var binding = helper.binding
        binding.setVariable(BR.typeImg,true)
        binding.executePendingBindings()
    }

    fun checkItem(position: Int) {
        // 选中某一个 item
        var temp: TypeImgBean?
        for (i in 0 until data.size) {
            temp = data[i]
            if (temp != null) {
                temp.isChecked = i == position
            }
        }
        mCurrentCheckPosition = position
        notifyDataSetChanged()
    }
}