package com.magicalrice.adolph.jbookkeeping.ui.statistic

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Adolph on 2018/7/13.
 */
class ViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val mFragments = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return mFragments.get(position)
    }

    override fun getCount(): Int {
        return mFragments.size
    }
}