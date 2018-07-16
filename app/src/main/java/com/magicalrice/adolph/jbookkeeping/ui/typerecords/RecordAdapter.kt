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

package com.magicalrice.adolph.jbookkeeping.ui.typerecords

import com.magicalrice.adolph.jbookkeeping.BR
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseDataBindingAdapter
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordWithType

/**
 * RecordAdapter
 *
 * @author bakumon https://bakumon.me
 * @date 2018/5/28
 */

class RecordAdapter(data: List<RecordWithType>?) : BaseDataBindingAdapter<RecordWithType>(R.layout.item_record_sort_money, data) {

    override fun convert(helper: BaseDataBindingAdapter.DataBindingViewHolder, item: RecordWithType) {
        val binding = helper.binding
        helper.addOnLongClickListener(R.id.ll_item_click)
        binding.setVariable(BR.recordWithType, item)
        binding.executePendingBindings()
    }
}
