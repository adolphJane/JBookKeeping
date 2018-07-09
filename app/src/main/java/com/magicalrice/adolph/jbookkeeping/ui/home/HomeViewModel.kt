package com.magicalrice.adolph.jbookkeeping.ui.home

import com.magicalrice.adolph.jbookkeeping.base.BaseViewModel
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordWithType
import com.magicalrice.adolph.jbookkeeping.database.entity.SumMoneyBean
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Adolph on 2018/7/6.
 */
class HomeViewModel(dataSource: AppDataSource) : BaseViewModel(dataSource) {
    val currentMonthRecordWithTypes: Flowable<List<RecordWithType>>
        get() = mDataSource.getCurrentMonthRecordWithTypes()

    val currentMonthSumMoney: Flowable<List<SumMoneyBean>>
        get() = mDataSource.getCurrentMonthSumMoney()

    fun initRecordTypes(): Completable {
        return mDataSource.initRecordTypes()
    }

    fun deleteRecord(record: RecordWithType): Completable {
        return mDataSource.deleteRecord(record)
    }
}