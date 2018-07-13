/*
 * Copyright 2018 Bakumon. https://github.com/Bakumon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.bakumon.moneykeeper.ui.typerecords

import android.os.Bundle
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.databinding.ActivityStatisticsBinding
import com.magicalrice.adolph.jbookkeeping.ui.statistic.ViewPagerAdapter

/**
 * 某一类型的记账记录
 *
 * @author Bakumon https://bakumon
 */
class TypeRecordsActivity : BaseActivity() {

    private lateinit var mBinding: ActivityStatisticsBinding

    private var mRecordType: Int = 0
    private var mRecordTypeId: Int = 0
    private var mYear: Int = 0
    private var mMonth: Int = 0

    override val layoutId: Int
        get() = R.layout.activity_statistics

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()

        initView()
    }

    private fun initView() {
        if (intent != null) {
            mBinding.titleBar?.title = intent.getStringExtra(RouterTable.ExtraKey.KEY_TYPE_NAME)
            mRecordType = intent.getIntExtra(RouterTable.ExtraKey.KEY_RECORD_TYPE, 0)
            mRecordTypeId = intent.getIntExtra(RouterTable.ExtraKey.KEY_RECORD_TYPE_ID, 0)
            mYear = intent.getIntExtra(RouterTable.ExtraKey.KEY_YEAR, 0)
            mMonth = intent.getIntExtra(RouterTable.ExtraKey.KEY_MONTH, 0)
        }

        mBinding.titleBar?.btnBack?.setOnClickListener { finish() }
        mBinding.typeChoice?.rbOutlay?.setText(R.string.text_sort_time)
        mBinding.typeChoice?.rbIncome?.setText(R.string.text_sort_money)

        setUpFragment()
    }

    private fun setUpFragment() {
        val infoPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        val timeSortFragment = TypeRecordsFragment.newInstance(TypeRecordsFragment.SORT_TIME, mRecordType, mRecordTypeId, mYear, mMonth)
        val moneySortFragment = TypeRecordsFragment.newInstance(TypeRecordsFragment.SORT_MONEY, mRecordType, mRecordTypeId, mYear, mMonth)
        infoPagerAdapter.addFragment(timeSortFragment)
        infoPagerAdapter.addFragment(moneySortFragment)
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
}
