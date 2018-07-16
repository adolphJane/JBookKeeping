package com.magicalrice.adolph.jbookkeeping.ui.typerecords

import com.magicalrice.adolph.jbookkeeping.base.BaseViewModel
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordWithType
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource
import com.magicalrice.adolph.jbookkeeping.utils.DateUtils
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * 某一类型的记账记录
 *
 * @author Bakumon https://bakumon.me
 */
class TypeRecordsViewModel(dataSource: AppDataSource) : BaseViewModel(dataSource) {

    fun getRecordWithTypes(sortType: Int, type: Int, typeId: Int, year: Int, month: Int): Flowable<List<RecordWithType>> {
        val dateFrom = DateUtils.getMonthStart(year, month)
        val dateTo = DateUtils.getMonthEnd(year, month)
        return if (sortType == TypeRecordsFragment.SORT_TIME) {
            mDataSource.getRecordWithTypes(dateFrom, dateTo, type, typeId)
        } else {
            mDataSource.getRecordWithTypesSortMoney(dateFrom, dateTo, type, typeId)
        }
    }

    fun deleteRecord(record: RecordWithType): Completable {
        return mDataSource.deleteRecord(record)
    }

}
