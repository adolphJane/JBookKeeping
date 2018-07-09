package com.magicalrice.adolph.jbookkeeping

import com.magicalrice.adolph.jbookkeeping.utils.SPUtils

/**
 * Created by Adolph on 2018/7/9.
 */
object ConfigManager {
    private const val SP_NAME = "config"
    private const val KEY_AUTO_BACKUP = "auto_backup"
    private const val KEY_SUCCESSIVE = "successive"
    private const val KEY_FAST = "fast"
    private const val KEY_BUDGET = "budget"

    val isAutoBackup: Boolean
        get() = SPUtils.getInstance(SP_NAME)!!.getBoolean(KEY_AUTO_BACKUP, true)
    val isFast: Boolean
        get() = SPUtils.getInstance(SP_NAME)!!.getBoolean(KEY_FAST)
    val isSuccessive: Boolean
        get() = SPUtils.getInstance(SP_NAME)!!.getBoolean(KEY_SUCCESSIVE, true)
    val budget: Int
        get() = SPUtils.getInstance(SP_NAME)!!.getInt(KEY_BUDGET, 0)

    /**
     * 自动备份
     */
    fun setIsAutoBackup(isAutoBackup: Boolean):Boolean {
        return SPUtils.getInstance(SP_NAME)!!.put(KEY_AUTO_BACKUP,isAutoBackup)
    }

    /**
     * 快速记账
     */
    fun setIsFast(isFast:Boolean):Boolean {
        return SPUtils.getInstance(SP_NAME)!!.put(KEY_FAST,isFast)
    }

    /**
     * 连续记账
     */
    fun setIsSuccessive(isSuccession: Boolean):Boolean {
        return SPUtils.getInstance(SP_NAME)!!.put(KEY_SUCCESSIVE,isSuccession)
    }

    /**
     * 月预算
     */
    fun setBudget(budget:Int):Boolean {
        return SPUtils.getInstance(SP_NAME)!!.put(KEY_BUDGET,budget)
    }
}