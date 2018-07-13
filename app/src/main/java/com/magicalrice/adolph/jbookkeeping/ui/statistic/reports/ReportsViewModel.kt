package com.magicalrice.adolph.jbookkeeping.ui.statistic.reports

import com.magicalrice.adolph.jbookkeeping.base.BaseViewModel
import com.magicalrice.adolph.jbookkeeping.database.entity.SumMoneyBean
import com.magicalrice.adolph.jbookkeeping.database.entity.TypeSumMoneyBean
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource
import com.magicalrice.adolph.jbookkeeping.utils.DateUtils
import io.reactivex.Flowable

/**
 * Created by Adolph on 2018/7/13.
 */
class ReportsViewModel(dataSource: AppDataSource): BaseViewModel(dataSource) {
    fun getMonthSumMoney(year: Int, month: Int): Flowable<List<SumMoneyBean>> {
        val dateFrom = DateUtils.getMonthStart(year, month)
        val dateTo = DateUtils.getMonthEnd(year, month)
        return mDataSource.getMonthSumMoney(dateFrom, dateTo)
    }

    fun getTypeSumMoney(year: Int, month: Int, type: Int): Flowable<List<TypeSumMoneyBean>> {
        val dateFrom = DateUtils.getMonthStart(year, month)
        val dateTo = DateUtils.getMonthEnd(year, month)
        return mDataSource.getTypeSumMoney(dateFrom, dateTo, type)
    }
}