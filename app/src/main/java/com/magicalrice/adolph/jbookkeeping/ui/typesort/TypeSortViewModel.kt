package com.magicalrice.adolph.jbookkeeping.ui.typesort

import com.magicalrice.adolph.jbookkeeping.base.BaseViewModel
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * 类型排序 ViewModel
 *
 * @author Bakumon https://bakumon.me
 */
class TypeSortViewModel(dataSource: AppDataSource) : BaseViewModel(dataSource) {

    fun getRecordTypes(type: Int): Flowable<List<RecordType>> {
        return mDataSource.getRecordTypes(type)
    }

    fun sortRecordTypes(recordTypes: List<RecordType>): Completable {
        return mDataSource.sortRecordTypes(recordTypes)
    }
}
