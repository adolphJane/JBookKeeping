package com.magicalrice.adolph.jbookkeeping.base

/**
 * 路由表
 * Created by Adolph on 2018/7/10.
 */
object RouterTable {

    object Url {
        //Group
        const val GROUP_MAIN = "/main"
        const val GROUP_SETTING = "/setting"
        const val GROUP_ADD = "/add"
        const val GROUP_STATISTIC = "/statistic"

        //Item
        const val ITEM_HOME = GROUP_MAIN + "/home"
        const val ITEM_SETTING = GROUP_SETTING + "/main"
        const val ITEM_ADD_RECORD = GROUP_ADD + "/record"
        const val ITEM_ADD_TYPE = GROUP_ADD + "/type"
        const val ITEM_STATISTIC = GROUP_STATISTIC + "/statistic"
        const val ITEM_TYPE_SORT = GROUP_SETTING + "/sort"
        const val ITEM_TYPE_MANAGE = GROUP_SETTING + "/manage"
        const val ITEM_ABOUT = GROUP_SETTING + "/about"
        const val ITEM_OPEN_SOURCE = GROUP_SETTING + "/open_source"
        const val ITEM_TYPE_RECORD = GROUP_ADD + "/type_record"
    }

    /**
     * 传递数据使用的 key
     */
    object ExtraKey {
        const val KEY_IS_SUCCESSIVE = "key_is_successive"
        const val KEY_RECORD_BEAN = "key_record_bean"
        const val KEY_TYPE = "key_type"
        const val KEY_TYPE_BEAN = "key_type_bean"
        const val KEY_TYPE_NAME = "key_type_name"
        const val KEY_RECORD_TYPE = "key_record_type"
        const val KEY_RECORD_TYPE_ID = "key_record_type_id"
        const val KEY_YEAR = "key_year"
        const val KEY_MONTH = "key_month"
        const val KEY_SORT_TYPE = "key_sort_type"
    }
}