package com.magicalrice.adolph.jbookkeeping.database.entity

import java.math.BigDecimal

/**
 * Created by Adolph on 2018/7/6.
 */
data class SumMoneyBean (
    //类型：0-支出，1-收入
    val type: Int,
    //支出或收入的总和
    val sumMoney: BigDecimal
)