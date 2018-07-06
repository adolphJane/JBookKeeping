package com.magicalrice.adolph.jbookkeeping.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordWithType
import io.reactivex.Flowable
import java.util.*

/**
 * Created by Adolph on 2018/7/6.
 */
@Dao
interface RecordDao {
    @Query("SELECT * FROM Record WHERE time BETWEEN :from AND :to ORDER BY time DESC,create_time DESC")
    fun getRangeRecordWithTypes(from: Date, to: Date): Flowable<List<RecordWithType>>

    @Query("SELECT Record.* from Record LEFT JOIN RecordType ON Record.record_type_id=RecordType.id WHERE (RecordType.type=:type AND time BETWEEN :from AND :to) ORDER BY time DESC, create_time DESC")
    fun getRangeRecordWithTypes(from: Date,to: Date,type: Int):Flowable<List<RecordWithType>>
}