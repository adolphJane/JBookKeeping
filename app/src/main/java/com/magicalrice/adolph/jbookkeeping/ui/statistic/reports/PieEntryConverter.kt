package com.magicalrice.adolph.jbookkeeping.ui.statistic.reports

import com.github.mikephil.charting.data.PieEntry
import com.magicalrice.adolph.jbookkeeping.database.entity.TypeSumMoneyBean
import java.util.*

/**
 * 饼状图数据转换器
 *
 * @author Bakumon https://bakumon.me
 */
object PieEntryConverter {
    /**
     * 获取饼状图所需数据格式 PieEntry
     *
     * @param typeSumMoneyBeans 类型汇总数据
     * @return List<PieEntry>
    </PieEntry> */
    fun getBarEntryList(typeSumMoneyBeans: List<TypeSumMoneyBean>): List<PieEntry> {
        val entryList = ArrayList<PieEntry>()
        for (i in typeSumMoneyBeans.indices) {
            val typeMoney = typeSumMoneyBeans[i].typeSumMoney
            entryList.add(PieEntry(typeMoney.toInt().toFloat(), typeSumMoneyBeans[i].typeName, typeSumMoneyBeans[i]))
        }
        return entryList
    }
}
