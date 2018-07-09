package com.magicalrice.adolph.jbookkeeping.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.magicalrice.adolph.jbookkeeping.BookKeepingApplication
import com.magicalrice.adolph.jbookkeeping.database.converters.Converters
import com.magicalrice.adolph.jbookkeeping.database.dao.RecordDao
import com.magicalrice.adolph.jbookkeeping.database.dao.RecordTypeDao
import com.magicalrice.adolph.jbookkeeping.database.entity.Record
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType

/**
 * Created by Adolph on 2018/7/6.
 */
@Database(entities = [Record::class,RecordType::class],version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * 获取记账类型操作类
     */
    abstract fun recordTypeDao():RecordTypeDao

    /**
     * 获取记账操作类
     */
    abstract fun recordDao(): RecordDao

    companion object {
        const val DB_NAME = "JBookKeeping.db"
        @Volatile
        private var INSTANCE: AppDatabase? = null
        val instance: AppDatabase?
        get() {
            if (INSTANCE == null)
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(BookKeepingApplication.instance,AppDatabase::class.java, DB_NAME)
                                .build()
                    }
                }
            return INSTANCE
        }
    }
}