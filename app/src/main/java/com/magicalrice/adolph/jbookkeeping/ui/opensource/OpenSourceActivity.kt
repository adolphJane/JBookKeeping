package com.magicalrice.adolph.jbookkeeping.ui.opensource

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.databinding.ActivityOpenSourceBinding
import com.magicalrice.adolph.jbookkeeping.utils.AndroidUtil

/**
 * 开源许可证
 *
 * @author Bakumon https://bakumon.me
 */
@Route(path = RouterTable.Url.ITEM_OPEN_SOURCE, name = "开源许可证")
class OpenSourceActivity : BaseActivity() {
    private lateinit var mBinding: ActivityOpenSourceBinding

    override val layoutId: Int
        get() = R.layout.activity_open_source

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()

        initView()
    }

    private fun initView() {
        mBinding.titleBar?.btnBack?.setOnClickListener { finish() }
        mBinding.titleBar?.title = getString(R.string.text_title_open_source)

        mBinding.rvOpenSource.layoutManager = LinearLayoutManager(this)
        val adapter = OpenSourceAdapter(OpenSourceListCreator.openSourceList)
        mBinding.rvOpenSource.adapter = adapter
        adapter.setOnItemClickListener { _, _, position -> AndroidUtil.openWeb(this, adapter.data[position].url) }
    }

}
