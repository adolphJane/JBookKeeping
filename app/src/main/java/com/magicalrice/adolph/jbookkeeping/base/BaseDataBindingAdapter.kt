package com.magicalrice.adolph.jbookkeeping.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.magicalrice.adolph.jbookkeeping.R

/**
 * Created by Adolph on 2018/7/6.
 */
abstract class BaseDataBindingAdapter<T>(layoutResId: Int, data: List<T>?) : BaseQuickAdapter<T, BaseDataBindingAdapter.DataBindingViewHolder>(layoutResId, data) {
    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        return view
    }

    class DataBindingViewHolder(view: View) : BaseViewHolder(view) {
        val binding: ViewDataBinding
            get() = itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
    }
}