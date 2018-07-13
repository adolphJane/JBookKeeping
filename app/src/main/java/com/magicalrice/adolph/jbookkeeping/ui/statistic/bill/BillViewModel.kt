package com.magicalrice.adolph.jbookkeeping.ui.statistic.bill

import com.magicalrice.adolph.jbookkeeping.base.BaseViewModel
import com.magicalrice.adolph.jbookkeeping.database.entity.DaySumMoneyBean
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordWithType
import com.magicalrice.adolph.jbookkeeping.database.entity.SumMoneyBean
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource
import com.magicalrice.adolph.jbookkeeping.utils.DateUtils
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Adolph on 2018/7/13.
 */
class BillViewModel(dataSource: AppDataSource) : BaseViewModel(dataSource) {
    fun getRecordWithTypes(year: Int, month: Int, type: Int): Flowable<List<RecordWithType>> {
        val dateFrom = DateUtils.getMonthStart(year, month)
        val dateTo = DateUtils.getMonthEnd(year, month)
        return mDataSource.getRecordWithTypes(dateFrom, dateTo, type)
    }

    fun getDaySumMoney(year: Int, month: Int, type: Int): Flowable<List<DaySumMoneyBean>> {
        return mDataSource.getDaySumMoney(year, month, type)
    }

    fun getMonthSumMoney(year: Int, month: Int): Flowable<List<SumMoneyBean>> {
        val dateFrom = DateUtils.getMonthStart(year, month)
        val dateTo = DateUtils.getMonthEnd(year, month)
        return mDataSource.getMonthSumMoney(dateFrom, dateTo)
    }

    fun deleteRecord(record: RecordWithType): Completable {
        return mDataSource.deleteRecord(record)
    }
}