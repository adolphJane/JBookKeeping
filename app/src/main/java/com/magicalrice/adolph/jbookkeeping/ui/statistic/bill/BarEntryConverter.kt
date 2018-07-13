package com.magicalrice.adolph.jbookkeeping.ui.statistic.bill

import com.github.mikephil.charting.data.BarEntry
import com.magicalrice.adolph.jbookkeeping.database.entity.DaySumMoneyBean
import com.magicalrice.adolph.jbookkeeping.utils.BigDecimalUtil
import java.util.ArrayList

/**
 * Created by Adolph on 2018/7/13.
 */

object BarEntryConverter {
    /**
     * 获取柱状图所需数据格式 BarEntry
     *
     * @param count            生成的数据 list 大小
     * @param daySumMoneyBeans 包含日期和该日期汇总数据
     * @return List<BarEntry>
    </BarEntry> */
    fun getBarEntryList(count: Int, daySumMoneyBeans: List<DaySumMoneyBean>?): List<BarEntry> {
        val entryList = ArrayList<BarEntry>()
        if (daySumMoneyBeans != null && daySumMoneyBeans.isNotEmpty()) {
            var barEntry: BarEntry
            for (i in 0 until count) {
                for (j in daySumMoneyBeans.indices) {
                    if (i + 1 == daySumMoneyBeans[j].time.date) {
                        val money = BigDecimalUtil.fen2YuanBD(daySumMoneyBeans[j].daySumMoney)
                        // 这里的 y 由于是 float，所以数值很大的话，还是会出现科学计数法
                        barEntry = BarEntry((i + 1).toFloat(), money.toFloat())
                        entryList.add(barEntry)
                    }
                }
                barEntry = BarEntry((i + 1).toFloat(), 0f)
                entryList.add(barEntry)
            }
        }
        return entryList
    }
}