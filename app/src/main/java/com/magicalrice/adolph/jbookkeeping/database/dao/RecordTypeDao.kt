package com.magicalrice.adolph.jbookkeeping.database.dao

import android.arch.persistence.room.*
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import io.reactivex.Flowable

/**
 * Created by Adolph on 2018/7/6.
 */
@Dao
interface RecordTypeDao {
    @Query("SELECT * FROM RecordType WHERE state = 0 ORDER BY ranking")
    fun getAllRecordTypes(): Flowable<List<RecordType>>

    @Query("SELECT count(RecordType.id) FROM RecordType")
    fun getRecordTypeCount(): Long

    @Query("SELECT * FROM RecordType WHERE state = 0 AND type = :type ORDER BY ranking")
    fun getRecordTypes(type: Int): Flowable<List<RecordType>>

    @Query("SELECT * FROM RecordType WHERE type = :type AND name = :name")
    fun getTypeByName(type: Int, name: String): RecordType?

    @Insert
    fun insertRecordTypes(vararg recordTypes: RecordType)

    @Update
    fun updateRecordTypes(vararg recordTypes: RecordType)

    @Delete
    fun deleteRecordType(recordType: RecordType)
}