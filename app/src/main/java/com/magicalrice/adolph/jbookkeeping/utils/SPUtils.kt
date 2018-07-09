package com.magicalrice.adolph.jbookkeeping.utils

import android.arch.persistence.room.PrimaryKey
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.magicalrice.adolph.jbookkeeping.BookKeepingApplication

/**
 * Created by Adolph on 2018/7/9.
 */
class SPUtils private constructor(spName: String) {
    private val sp: SharedPreferences = BookKeepingApplication.instance.getSharedPreferences(spName, Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var INSTANCE: SPUtils? = null

        fun getInstance(spName: String): SPUtils? {
            if (INSTANCE == null) {
                synchronized(SPUtils::class) {
                    if (INSTANCE == null) {
                        INSTANCE = SPUtils(if (TextUtils.isEmpty(spName)) "spUtils" else spName)
                    }
                }
            }
            return INSTANCE
        }
    }

    fun put(key: String, value: String): Boolean {
        return sp.edit().putString(key, value).commit()
    }

    @JvmOverloads
    fun getString(key: String, defaultValue: String = ""): String? {
        return sp.getString(key, defaultValue)
    }

    fun put(key: String, value: Int): Boolean {
        return sp.edit().putInt(key, value).commit()
    }

    @JvmOverloads
    fun getInt(key: String, defaultValue: Int = -1): Int {
        return sp.getInt(key, defaultValue)
    }

    fun put(key: String, value: Boolean): Boolean {
        return sp.edit().putBoolean(key, value).commit()
    }

    @JvmOverloads
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sp.getBoolean(key, defaultValue)
    }
}