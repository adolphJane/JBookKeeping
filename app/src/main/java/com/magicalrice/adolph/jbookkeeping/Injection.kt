package com.magicalrice.adolph.jbookkeeping

import com.magicalrice.adolph.jbookkeeping.database.AppDatabase
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource
import com.magicalrice.adolph.jbookkeeping.datasource.LocalAppDataSource
import com.magicalrice.adolph.jbookkeeping.viewmodel.ViewModelFactory

/**
 * Created by Adolph on 2018/7/6.
 */
object Injection {
    fun provideUserDataSource(): AppDataSource {
        val dataSource = AppDatabase.instance!!
        return LocalAppDataSource(dataSource)
    }

    fun provideViewModelFactory(): ViewModelFactory {
        val dataSource = provideUserDataSource()
        return ViewModelFactory(dataSource)
    }
}