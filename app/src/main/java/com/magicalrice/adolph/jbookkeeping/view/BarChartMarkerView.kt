package com.magicalrice.adolph.jbookkeeping.view

import android.content.Context
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.magicalrice.adolph.jbookkeeping.R

/**
 * Created by Adolph on 2018/7/13.
 */
class BarChartMarkerView(context: Context): MarkerView(context, R.layout.bar_chart_marker_view) {
    private val tvContent: TextView = findViewById(R.id.tv_content)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val content = e!!.x.toInt().toString() + context.getString(R.string.text_day) + resources.getString(R.string.text_money_symbol) + e.y
        tvContent.text = content
        if (e.y > 0) {
            tvContent.visibility = View.VISIBLE
        } else {
            tvContent.visibility = View.GONE
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}