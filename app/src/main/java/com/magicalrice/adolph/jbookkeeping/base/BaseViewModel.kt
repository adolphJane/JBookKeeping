package com.magicalrice.adolph.jbookkeeping.base

import android.arch.lifecycle.ViewModel
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource

/**
 * Created by Adolph on 2018/7/6.
 */
open class BaseViewModel(protected var mDataSource: AppDataSource) : ViewModel()