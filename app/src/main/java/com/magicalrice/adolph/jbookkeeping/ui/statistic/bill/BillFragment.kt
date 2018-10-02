package com.magicalrice.adolph.jbookkeeping.ui.statistic.bill

import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.android.arouter.launcher.ARouter
import com.android.databinding.library.baseAdapters.BR
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.magicalrice.adolph.jbookkeeping.Injection
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseFragment
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.database.entity.DaySumMoneyBean
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordWithType
import com.magicalrice.adolph.jbookkeeping.databinding.FragmentBillBinding
import com.magicalrice.adolph.jbookkeeping.datasource.BackupFailException
import com.magicalrice.adolph.jbookkeeping.ui.home.HomeAdapter
import com.magicalrice.adolph.jbookkeeping.utils.DateUtils
import com.magicalrice.adolph.jbookkeeping.utils.ToastUtils
import com.magicalrice.adolph.jbookkeeping.view.BarChartMarkerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * 账单Fragment
 * Created by Adolph on 2018/7/13.
 */
class BillFragment : BaseFragment() {
    private lateinit var mBinding: FragmentBillBinding
    private lateinit var mViewModel: BillViewModel
    private lateinit var mAdapter: HomeAdapter

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mType: Int = 0

    override val layoutId: Int
        get() = R.layout.fragment_bill

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()
        val viewModelFactory = Injection.provideViewModelFactory()
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(BillViewModel::class.java)

        mYear = DateUtils.getCurrentYear()
        mMonth = DateUtils.getCurrentMonth()
        mType = RecordType.TYPE_OUTLAY

        initView()
    }

    private fun initView() {
        mBinding.rvRecordBill.layoutManager = LinearLayoutManager(context)
        mAdapter = HomeAdapter(null)
        mBinding.rvRecordBill.adapter = mAdapter
        mAdapter.setOnItemChildLongClickListener { _, _, position ->
            showOperateDialog(mAdapter.data[position])
            false
        }

        initBarChart()

        mBinding.layoutSumMoney?.rgType?.setOnCheckedChangeListener { _, checkedId ->
            mType = if (checkedId == R.id.rb_outlay) {
                RecordType.TYPE_OUTLAY
            } else {
                RecordType.TYPE_INCOME
            }
            getOrderData()
            getDaySumData()
            getMonthSumMoney()
        }
    }

    private fun initBarChart() {
        mBinding.barChart.setNoDataText("")
        mBinding.barChart.setScaleEnabled(false)
        mBinding.barChart.description.isEnabled = false
        mBinding.barChart.legend.isEnabled = false

        mBinding.barChart.axisLeft.axisMinimum = 0f
        mBinding.barChart.axisLeft.isEnabled = false
        mBinding.barChart.axisRight.isEnabled = false
        val xAxis = mBinding.barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            xAxis.textColor = resources.getColor(R.color.colorTextGray, null)
        } else {
            xAxis.textColor = resources.getColor(R.color.colorTextGray)
        }
        xAxis.labelCount = 5

        val mv = BarChartMarkerView(context!!)
        mv.chartView = mBinding.barChart
        mBinding.barChart.marker = mv
    }

    private fun showOperateDialog(record: RecordWithType) {
        if (context == null) {
            return
        }
        MaterialDialog.Builder(context!!)
                .items(getString(R.string.text_modify), getString(R.string.text_delete))
                .itemsCallback({ _, _, which, _ ->
                    if (which == 0) {
                        modifyRecord(record)
                    } else {
                        deleteRecord(record)
                    }
                })
                .show()
    }

    private fun modifyRecord(record: RecordWithType) {
        if (context == null) {
            return
        }
        ARouter.getInstance().build(RouterTable.Url.ITEM_ADD_RECORD).withSerializable(RouterTable.ExtraKey.KEY_RECORD_BEAN, record).navigation()
    }

    private fun deleteRecord(record: RecordWithType) {
        mDisposable.add(mViewModel.deleteRecord(record)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }
                ) { throwable ->
                    if (throwable is BackupFailException) {
                        ToastUtils.show(throwable.message)
                        Log.e(TAG, "备份失败（删除记账记录失败的时候）", throwable)
                    } else {
                        ToastUtils.show(R.string.toast_record_delete_fail)
                        Log.e(TAG, "删除记账记录失败", throwable)
                    }
                })
    }

    private fun setChartData(daySumMoneyBeans: List<DaySumMoneyBean>?) {
        if (daySumMoneyBeans == null || daySumMoneyBeans.isEmpty()) {
            mBinding.barChart.visibility = View.INVISIBLE
            return
        } else {
            mBinding.barChart.visibility = View.VISIBLE
        }

        val count = DateUtils.getDayCount(mYear, mMonth)
        val barEntries = BarEntryConverter.getBarEntryList(count, daySumMoneyBeans)

        val set1: BarDataSet
        if (mBinding.barChart.data != null && mBinding.barChart.data.dataSetCount > 0) {
            set1 = mBinding.barChart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = barEntries
            mBinding.barChart.data.notifyDataChanged()
            mBinding.barChart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(barEntries, "")
            set1.setDrawIcons(false)
            set1.setDrawValues(false)
            if (Build.VERSION.SDK_INT >= 23) {
                set1.color = resources.getColor(R.color.colorAccent, null)
            } else {
                set1.color = resources.getColor(R.color.colorAccent)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                set1.valueTextColor = resources.getColor(R.color.colorTextWhite, null)
            } else {
                set1.valueTextColor = resources.getColor(R.color.colorTextWhite)
            }

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.barWidth = 0.5f
            data.isHighlightEnabled = true
            mBinding.barChart.data = data
        }
        mBinding.barChart.invalidate()
        mBinding.barChart.animateY(1000)
    }

    /**
     * 设置月份
     */
    fun setYearMonth(year: Int, month: Int) {
        if (year == mYear && month == mMonth) {
            return
        }
        mYear = year
        mMonth = month
        // 更新数据
        getOrderData()
        getDaySumData()
        getMonthSumMoney()
    }

    override fun lazyInitData() {
        mBinding.layoutSumMoney?.rgType?.check(R.id.rb_outlay)
    }

    private fun getOrderData() {
        mDisposable.add(mViewModel.getRecordWithTypes(mYear, mMonth, mType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ recordWithTypes ->
                    mAdapter.setNewData(recordWithTypes)
                    if (recordWithTypes.isEmpty()) {
                        mAdapter.emptyView = inflate(R.layout.layout_statistics_empty)
                    }
                }
                ) { throwable ->
                    ToastUtils.show(R.string.toast_records_fail)
                    Log.e(TAG, "获取记录列表失败", throwable)
                })
    }

    private fun getDaySumData() {
        mDisposable.add(mViewModel.getDaySumMoney(mYear, mMonth, mType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.setChartData(it) }
                ) { throwable ->
                    ToastUtils.show(R.string.toast_get_statistics_fail)
                    Log.e(TAG, "获取统计数据失败", throwable)
                })
    }

    private fun getMonthSumMoney() {
        mDisposable.add(mViewModel.getMonthSumMoney(mYear, mMonth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // 这种方式被误报红
                    // mBinding.layoutSumMoney?.sumMoneyBeanList = it
                    mBinding.layoutSumMoney?.setVariable(BR.sumMoneyBeanList, it)
                }
                ) { throwable ->
                    ToastUtils.show(R.string.toast_get_month_summary_fail)
                    Log.e(TAG, "获取该月汇总数据失败", throwable)
                })
    }

    companion object {
        private val TAG = BillFragment::class.java.simpleName
    }
}