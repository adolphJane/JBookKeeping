package com.magicalrice.adolph.jbookkeeping.utils

import com.magicalrice.adolph.jbookkeeping.BookKeepingApplication
import com.magicalrice.adolph.jbookkeeping.BuildConfig
import com.magicalrice.adolph.jbookkeeping.database.AppDatabase
import com.magicalrice.adolph.jbookkeeping.ui.setting.BackupBean
import com.snatik.storage.Storage
import org.ocpsoft.prettytime.PrettyTime
import java.io.File
import java.util.*

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
        val dir = storage.externalStorageDirectory + File.separator + BACKUP_DIR
        val backupBeans = ArrayList<BackupBean>()
        var bean: BackupBean
        val files = storage.getFiles(dir,"[\\S]*\\.db") ?: return backupBeans
        var fileTemp: File
        val prettyTime = PrettyTime()
        for (i in files.indices) {
            fileTemp = files[i]
            bean = BackupBean(
                    fileTemp,
                    fileTemp.name,
                    storage.getReadableSize(fileTemp),
                    prettyTime.format(Date(fileTemp.lastModified()))
            )
            backupBeans.add(bean)
        }
        return backupBeans
    }

    private fun backupDB(fileName: String):Boolean {
        val storage = Storage(BookKeepingApplication.instance)
        val isWritable = Storage.isExternalWritable()
        if (!isWritable) {
            return false
        }
        val path = storage.externalStorageDirectory + File.separator + BACKUP_DIR
        if (!storage.isDirectoryExists(path)) {
            storage.createDirectory(path)
        }
        val filePath = path + File.separator + fileName
        if (!storage.isFileExist(filePath)) {
            // 创建空文件，在模拟器上测试，如果没有这个文件，复制的时候会报 FileNotFound
            storage.createFile(filePath,"")
        }
        return storage.copy(BookKeepingApplication.instance.getDatabasePath(AppDatabase.DB_NAME)?.path,path + File.separator + fileName)
    }

    fun autoBackup() : Boolean {
        val fileName = AUTO_BACKUP_PREFIX + SUFFIX
        return backupDB(fileName)
    }

    fun userBackup() : Boolean {
        val fileName = USER_BACKUP_PREFIX + SUFFIX
        return backupDB(fileName)
    }

    fun restoreDB(restoreFile: String): Boolean {
        val storage = Storage(BookKeepingApplication.instance)
        if (storage.isFileExist(restoreFile)) {
            return storage.copy(restoreFile,BookKeepingApplication.instance.getDatabasePath(AppDatabase.DB_NAME)?.path)
        }
        return false
    }
}