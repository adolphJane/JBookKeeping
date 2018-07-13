package com.magicalrice.adolph.jbookkeeping.ui.add

import com.magicalrice.adolph.jbookkeeping.base.BaseDataBindingAdapter
import com.magicalrice.adolph.jbookkeeping.base.BaseViewModel
import com.magicalrice.adolph.jbookkeeping.database.entity.Record
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Adolph on 2018/7/13.
 */
class AddRecordViewModel(dataSource: AppDataSource) : BaseViewModel(dataSource) {
    val allRecordTypes: Flowable<List<RecordType>>
        get() = mDataSource.getAllRecordType()

    fun insertRecord(record: Record): Completable {
        return mDataSource.insertRecord(record)
    }

    fun updateRecord(record: Record): Completable {
        return mDataSource.updateRecord(record)
    }
}