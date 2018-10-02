package com.magicalrice.adolph.jbookkeeping.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource
import com.magicalrice.adolph.jbookkeeping.ui.add.AddRecordViewModel
import com.magicalrice.adolph.jbookkeeping.ui.addtype.AddTypeViewModel
import com.magicalrice.adolph.jbookkeeping.ui.home.HomeViewModel
import com.magicalrice.adolph.jbookkeeping.ui.statistic.bill.BillViewModel
import com.magicalrice.adolph.jbookkeeping.ui.statistic.reports.ReportsViewModel
import com.magicalrice.adolph.jbookkeeping.ui.typemanage.TypeManageViewModel
import com.magicalrice.adolph.jbookkeeping.ui.typerecords.TypeRecordsViewModel
import com.magicalrice.adolph.jbookkeeping.ui.typesort.TypeSortViewModel

/**
 * ViewModel工厂
 * Created by Adolph on 2018/7/9.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val mDataSource: AppDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddRecordViewModel::class.java) -> AddRecordViewModel(mDataSource) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(mDataSource) as T
            modelClass.isAssignableFrom(TypeManageViewModel::class.java) -> TypeManageViewModel(mDataSource) as T
            modelClass.isAssignableFrom(TypeSortViewModel::class.java) -> TypeSortViewModel(mDataSource) as T
            modelClass.isAssignableFrom(AddTypeViewModel::class.java) -> AddTypeViewModel(mDataSource) as T
            modelClass.isAssignableFrom(BillViewModel::class.java) -> BillViewModel(mDataSource) as T
            modelClass.isAssignableFrom(ReportsViewModel::class.java) -> ReportsViewModel(mDataSource) as T
            modelClass.isAssignableFrom(TypeRecordsViewModel::class.java) -> TypeRecordsViewModel(mDataSource) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}