package com.magicalrice.adolph.jbookkeeping.database

import android.arch.persistence.room.RoomDatabase
import com.magicalrice.adolph.jbookkeeping.database.dao.RecordDao
import com.magicalrice.adolph.jbookkeeping.database.dao.RecordTypeDao

/**
 * Created by Adolph on 2018/7/6.
 */

abstract class AppDatabase : RoomDatabase() {
    /**
     * 获取记账类型操作类
     */
    abstract fun recordTypeDao():RecordTypeDao

    /**
     * 获取记账操作类
     */
    abstract fun recordDao(): RecordDao
}