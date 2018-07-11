package com.magicalrice.adolph.jbookkeeping.ui.setting

import android.arch.lifecycle.ViewModel
import com.magicalrice.adolph.jbookkeeping.utils.BackupUtil
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Adolph on 2018/7/10.
 */
class SettingViewModel : ViewModel(){
    val backupFiles: Flowable<List<BackupBean>>
    get() = Flowable.create({
        e -> e.onNext(BackupUtil.getBackupFiles())
        e.onComplete()
    },BackpressureStrategy.BUFFER)

    fun backupDB(): Completable {
        return Completable.create {
            e ->
            val result = BackupUtil.userBackup()
            if (result) {
                e.onComplete()
            } else {
                e.onError(Exception())
            }
        }
    }

    fun restoreDB(restoreFile: String):Completable {
        return Completable.create {
            e ->
            val result = BackupUtil.restoreDB(restoreFile)
            if (result) {
                e.onComplete()
            } else {
                e.onError(Exception())
            }
        }
    }
}