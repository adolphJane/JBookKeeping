package com.magicalrice.adolph.jbookkeeping.database.dao

import android.arch.persistence.room.*
import com.magicalrice.adolph.jbookkeeping.database.entity.*
import io.reactivex.Flowable
import java.util.*

/**
 * Created by Adolph on 2018/7/6.
 */
@Dao
interface RecordDao {
    @Transaction
    @Query("SELECT * FROM Record WHERE time BETWEEN :from AND :to ORDER BY time DESC,create_time DESC")
    fun getRangeRecordWithTypes(from: Date, to: Date): Flowable<List<RecordWithType>>

    @Transaction
    @Query("SELECT Record.* from Record LEFT JOIN RecordType ON Record.record_type_id=RecordType.id WHERE (RecordType.type=:type AND time BETWEEN :from AND :to) ORDER BY time DESC, create_time DESC")
    fun getRangeRecordWithTypes(from: Date,to: Date,type: Int):Flowable<List<RecordWithType>>

    @Transaction
    @Query("SELECT Record.* from Record LEFT JOIN RecordType ON Record.record_type_id=RecordType.id WHERE (RecordType.type=:type AND Record.record_type_id=:typeId AND time BETWEEN :from AND :to) ORDER BY time DESC, create_time DESC")
    fun getRangeRecordWithTypes(from: Date, to: Date, type: Int, typeId: Int): Flowable<List<RecordWithType>>

    @Transaction
    @Query("SELECT Record.* from Record LEFT JOIN RecordType ON Record.record_type_id=RecordType.id WHERE (RecordType.type=:type AND Record.record_type_id=:typeId AND time BETWEEN :from AND :to) ORDER BY money DESC, create_time DESC")
    fun getRecordWithTypesSortMoney(from: Date, to: Date, type: Int, typeId: Int): Flowable<List<RecordWithType>>

    @Insert
    fun insertRecord(record: Record)

    @Update
    fun updateRecords(vararg records: Record)

    @Delete
    fun deleteRecord(record: Record)

    @Query("SELECT RecordType.type AS type, sum(Record.money) AS sumMoney FROM Record LEFT JOIN RecordType ON Record.record_type_id=RecordType.id WHERE time BETWEEN :from AND :to GROUP BY RecordType.type")
    fun getSumMoney(from: Date, to: Date): Flowable<List<SumMoneyBean>>

    @Query("SELECT count(id) FROM Record WHERE record_type_id = :typeId")
    fun getRecordCountWithTypeId(typeId: Int): Long

    @Query("SELECT * FROM Record WHERE record_type_id = :typeId")
    fun getRecordsWithTypeId(typeId: Int): List<Record>?

    /**
     * 尽量使用 Flowable 返回，因为当数据库数据改变时，会自动回调
     * 而直接用 List ，在调用的地方自己写 Flowable 不会自动回调
     */
    @Query("SELECT RecordType.type AS type, Record.time AS time, sum(Record.money) AS daySumMoney FROM Record LEFT JOIN RecordType ON Record.record_type_id=RecordType.id where (RecordType.type=:type and Record.time BETWEEN :from AND :to) GROUP BY Record.time")
    fun getDaySumMoney(from: Date, to: Date, type: Int): Flowable<List<DaySumMoneyBean>>

    @Query("SELECT t_type.img_name AS imgName,t_type.name AS typeName, Record.record_type_id AS typeId,sum(Record.money) AS typeSumMoney, count(Record.record_type_id) AS count FROM Record LEFT JOIN RecordType AS t_type ON Record.record_type_id=t_type.id where (t_type.type=:type and Record.time BETWEEN :from AND :to) GROUP by Record.record_type_id Order by sum(Record.money) DESC")
    fun getTypeSumMoney(from: Date, to: Date, type: Int): Flowable<List<TypeSumMoneyBean>>
}