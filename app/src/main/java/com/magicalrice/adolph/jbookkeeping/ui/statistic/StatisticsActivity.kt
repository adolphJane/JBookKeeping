package com.magicalrice.adolph.jbookkeeping.ui.statistic

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.databinding.ActivityStatisticsBinding
import com.magicalrice.adolph.jbookkeeping.ui.statistic.bill.BillFragment
import com.magicalrice.adolph.jbookkeeping.ui.statistic.reports.ReportsFragment
import com.magicalrice.adolph.jbookkeeping.utils.DateUtils

/**
 * Created by Adolph on 2018/7/13.
 */
@Route(path = RouterTable.Url.ITEM_STATISTIC, name = "数据分析")
class StatisticsActivity : BaseActivity() {
    private lateinit var mBinding: ActivityStatisticsBinding
    private lateinit var mBillFragment: BillFragment
    private lateinit var mReportsFragment: ReportsFragment
    private var mCurrentYear = DateUtils.getCurrentYear()
    private var mCurrentMonth = DateUtils.getCurrentMonth()

    override val layoutId: Int
        get() = R.layout.activity_statistics

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()

        initView()
    }

    private fun initView() {
        val title = DateUtils.getCurrentYearMonth()
        mBinding.titleBar?.title = title
        mBinding.titleBar?.ivTitle?.visibility = View.VISIBLE
        mBinding.titleBar?.llTitle?.setOnClickListener { chooseMonth() }
        mBinding.titleBar?.btnBack?.setOnClickListener { finish() }
        mBinding.typeChoice?.rbOutlay?.setText(R.string.text_order)
        mBinding.typeChoice?.rbIncome?.setText(R.string.text_reports)

        setUpFragment()
    }

    private fun setUpFragment() {
        val infoPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        mBillFragment = BillFragment()
        mReportsFragment = ReportsFragment()
        infoPagerAdapter.addFragment(mBillFragment)
        infoPagerAdapter.addFragment(mReportsFragment)
        mBinding.viewPager.adapter = infoPagerAdapter
        mBinding.viewPager.offscreenPageLimit = 2

        mBinding.typeChoice?.rgType?.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_outlay) {
                mBinding.viewPager.setCurrentItem(0, false)
            } else {
                mBinding.viewPager.setCurrentItem(1, false)
            }
        }
        mBinding.typeChoice?.rgType?.check(R.id.rb_outlay)
    }

    private fun chooseMonth() {
        mBinding.titleBar?.llTitle?.isEnabled = false
        val chooseMonthDialog = ChooseMonthDialog(this, mCurrentYear, mCurrentMonth)
        chooseMonthDialog.mOnDismissListener = {
            mBinding.titleBar?.llTitle?.isEnabled = true
        }
        chooseMonthDialog.mOnChooseListener = { year, month ->
            mCurrentYear = year
            mCurrentMonth = month
            mBinding.titleBar?.title = DateUtils.getYearMonthFormatString(year, month)
            mBillFragment.setYearMonth(year, month)
            mReportsFragment.setYearMonth(year, month)
        }
        chooseMonthDialog.show()
    }
}