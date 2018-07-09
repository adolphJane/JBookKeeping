package com.magicalrice.adolph.jbookkeeping.datasource

import com.magicalrice.adolph.jbookkeeping.BookKeepingApplication
import com.magicalrice.adolph.jbookkeeping.R

/**
 * Created by Adolph on 2018/7/9.
 */
class BackupFailException : Exception(BookKeepingApplication.instance.getString(R.string.text_tip_backup_fail))