package com.magicalrice.adolph.jbookkeeping.database.converters

import android.arch.persistence.room.TypeConverter
import java.math.BigDecimal
import java.util.*

/**
 * Created by Adolph on 2018/7/9.
 */
object Converters {
    @JvmStatic
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @JvmStatic
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @JvmStatic
    @TypeConverter
    fun stringToBig(intDecimal: Int): BigDecimal {
        return BigDecimal(intDecimal)
    }

    @JvmStatic
    @TypeConverter
    fun bigToString(bigDecimal: BigDecimal): Int {
        return bigDecimal.toInt()
    }
}