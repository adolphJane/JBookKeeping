package com.magicalrice.adolph.jbookkeeping.utils

import com.magicalrice.adolph.jbookkeeping.BookKeepingApplication
import com.magicalrice.adolph.jbookkeeping.BuildConfig
import com.magicalrice.adolph.jbookkeeping.ui.setting.BackupBean
import com.snatik.storage.Storage

/**
 * 备份相关工具类
 * Created by Adolph on 2018/7/9.
 */
object BackupUtil {
    private val BACKUP_DIR = if (BuildConfig.DEBUG) "backup_moneykeeper_debug" else "backup_moneykeeper"
    private val AUTO_BACKUP_PREFIX = if (BuildConfig.DEBUG) "MoneyKeeperBackupAutoDebug" else "MoneyKeeperBackupAuto"
    private val USER_BACKUP_PREFIX = if (BuildConfig.DEBUG) "MoneyKeeperBackupUserDebug" else "MoneyKeeperBackupUser"
    private const val SUFFIX = ".db"

    fun getBackupFiles(): List<BackupBean> {
        val storage = Storage(BookKeepingApplication.instance)
    }
}