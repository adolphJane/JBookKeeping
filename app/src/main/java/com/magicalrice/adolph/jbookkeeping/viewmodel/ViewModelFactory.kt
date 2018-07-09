package com.magicalrice.adolph.jbookkeeping.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource
import com.magicalrice.adolph.jbookkeeping.ui.home.HomeViewModel

/**
 * Created by Adolph on 2018/7/9.
 */
class ViewModelFactory(private val mDataSource: AppDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(mDataSource) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}