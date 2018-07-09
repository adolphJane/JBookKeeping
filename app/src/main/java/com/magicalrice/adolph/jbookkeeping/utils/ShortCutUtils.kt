package com.magicalrice.adolph.jbookkeeping.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutManager
import android.os.Build

/**
 * Created by Adolph on 2018/7/9.
 */
object ShortCutUtils {
    @TargetApi(Build.VERSION_CODES.N_MR1)
    fun addRecordShortcut(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return
        }
        val shortcutManager = context.getSystemService(ShortcutManager::class.java)
//        val intentAdd = Intent(context,add)
//        intentAdd.action = "LOCATION_SHORTCUT"
    }
}