package com.magicalrice.adolph.jbookkeeping.ui.setting

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * Created by Adolph on 2018/7/11.
 */
class SettingSectionEntity : SectionEntity<SettingSectionEntity.Item> {
    constructor(header: String) : super(true,header)
    constructor(item: Item) : super(item)

    class Item {
        var title: String? = null
        var content: String?
        var isShowSwitch:Boolean = false
        var isConfigOpen:Boolean = false

        constructor(content:String) {
            this.title = null
            this.content = content
            this.isShowSwitch = false
            this.isConfigOpen = false
        }

        constructor(title: String, content: String?) {
            this.title = title
            this.content = content
            this.isShowSwitch = false
            this.isConfigOpen = false
        }

        constructor(title: String, content: String, isConfigOpen: Boolean) {
            this.title = title
            this.content = content
            this.isShowSwitch = true
            this.isConfigOpen = isConfigOpen
        }
    }
}