package com.magicalrice.adolph.jbookkeeping.ui.setting

import java.io.File

/**
 * Created by Adolph on 2018/7/9.
 */
data class BackupBean(
        val file: File,
        val name: String,
        val time: String,
        val size: String
)