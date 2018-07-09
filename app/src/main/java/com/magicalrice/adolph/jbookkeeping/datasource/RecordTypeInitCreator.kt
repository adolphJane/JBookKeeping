package com.magicalrice.adolph.jbookkeeping.datasource

import com.magicalrice.adolph.jbookkeeping.BookKeepingApplication
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType

/**
 * 产生初始化的记录类型数据
 * Created by Adolph on 2018/7/9.
 */
object RecordTypeInitCreator {
    fun createRecordTypeData(): Array<RecordType> {
        val list = ArrayList<RecordType>()
        val res = BookKeepingApplication.instance.resources
        var type: RecordType

        //支出
        type = RecordType(res.getString(R.string.text_type_eat), "type_eat", 0, 0)
        list.add(type)

        type = RecordType(res.getString(R.string.text_type_calendar), "type_calendar", 0, 1)
        list.add(type)

        type = RecordType(res.getString(R.string.text_type_3c), "type_3c", 0, 2)
        list.add(type)

        type = RecordType(res.getString(R.string.text_type_clothes), "type_clothes", 0, 3)
        list.add(type)

        type = RecordType(res.getString(R.string.text_type_pill), "type_pill", 0, 4)
        list.add(type)

        type = RecordType(res.getString(R.string.text_type_candy), "type_candy", 0, 5)
        list.add(type)

        type = RecordType(res.getString(R.string.text_type_humanity), "type_humanity", 0, 6)
        list.add(type)

        type = RecordType(res.getString(R.string.text_type_pet), "type_pet", 0, 7)
        list.add(type)

        // 收入
        type = RecordType(res.getString(R.string.text_type_salary), "type_salary", 1, 0)
        list.add(type)

        type = RecordType(res.getString(R.string.text_type_pluralism), "type_pluralism", 1, 1)
        list.add(type)

        return list.toTypedArray()
    }
}