package com.magicalrice.adolph.jbookkeeping.ui.setting

import android.text.TextUtils
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.magicalrice.adolph.jbookkeeping.R

/**
 * Created by Adolph on 2018/7/11.
 */
class SettingAdapter(data:List<SettingSectionEntity>?) : BaseSectionQuickAdapter<SettingSectionEntity,BaseViewHolder>(R.layout.item_setting,R.layout.item_setting_header,data) {
    override fun convertHead(helper: BaseViewHolder, item: SettingSectionEntity) {
        val llHead = helper.getView<LinearLayout>(R.id.ll_head)
        if (helper.adapterPosition == 0) {
            llHead.setPadding(llHead.paddingLeft,0,llHead.paddingRight,llHead.paddingBottom)
        } else {
            llHead.setPadding(llHead.paddingLeft,llHead.paddingTop,llHead.paddingRight,llHead.paddingBottom)
        }
        helper.setText(R.id.tv_head,item.header)
    }

    override fun convert(helper: BaseViewHolder, item: SettingSectionEntity) {
        helper.setText(R.id.tv_title,item.t.title)
                .setGone(R.id.tv_title,!TextUtils.isEmpty(item.t.title))
                .setText(R.id.tv_content,item.t.content)
                .setGone(R.id.tv_content,!TextUtils.isEmpty(item.t.content))
                .setVisible(R.id.switch_item,item.t.isShowSwitch)
                .setChecked(R.id.switch_item,item.t.isConfigOpen)
                .addOnClickListener(R.id.switch_item)
    }
}