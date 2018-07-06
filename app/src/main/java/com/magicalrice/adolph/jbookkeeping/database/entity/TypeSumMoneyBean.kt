package com.magicalrice.adolph.jbookkeeping.database.entity

import java.math.BigDecimal

/**
 * Created by Adolph on 2018/7/6.
 */
data class TypeSumMoneyBean(
        val imgName: String,
        val typeName: String,
        val typeSumMoney: BigDecimal,
        val typeId: Int,
        val count: Int
)