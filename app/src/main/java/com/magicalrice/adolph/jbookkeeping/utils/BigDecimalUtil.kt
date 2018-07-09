package com.magicalrice.adolph.jbookkeeping.utils

import android.text.TextUtils
import java.math.BigDecimal

/**
 * Created by Adolph on 2018/7/9.
 */
object BigDecimalUtil {
    /**
     * 分转换为元
     */
    fun fen2Yuan(fenBD:BigDecimal?):String {
        return if (fenBD != null) {
            fenBD.divide(BigDecimal(100)).toPlainString()
        } else {
            "0"
        }
    }

    /**
     * 分转换为元
     */
    fun fen2YuanBD(fenBD: BigDecimal?): BigDecimal {
        return if (fenBD != null) {
            fenBD.divide(BigDecimal(100))
        } else {
            BigDecimal(0)
        }
    }

    /**
     * 元转换为分
     */
    fun yuan2FenBD(strYuan: String): BigDecimal {
        return if (!TextUtils.isEmpty(strYuan)) {
            BigDecimal(strYuan).multiply(BigDecimal(100))
        } else {
            BigDecimal(0)
        }
    }
}