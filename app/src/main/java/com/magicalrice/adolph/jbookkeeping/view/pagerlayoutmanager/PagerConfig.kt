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

package com.magicalrice.adolph.jbookkeeping.view.pagerlayoutmanager

import android.util.Log

/**
 * 作用：Pager配置
 * 作者：GcsSloop
 * 摘要：主要用于Log的显示与关闭
 */
object PagerConfig {
    private val TAG = "PagerGrid"
    /**
     * 判断是否输出日志
     *
     * @return true 输出，false 不输出
     */
    /**
     * 设置是否输出日志
     *
     * @param showLog 是否输出
     */
    var isShowLog = false
    /**
     * 获取当前滚动速度阀值
     *
     * @return 当前滚动速度阀值
     */
    /**
     * 设置当前滚动速度阀值
     *
     * @param flingThreshold 滚动速度阀值
     */
    var flingThreshold = 1000          // Fling 阀值，滚动速度超过该阀值才会触发滚动
    /**
     * 获取滚动速度 英寸/微秒
     *
     * @return 英寸滚动速度
     */
    /**
     * 设置像素滚动速度 英寸/微秒
     *
     * @param millisecondsPreInch 英寸滚动速度
     */
    var millisecondsPreInch = 60f    // 每一个英寸滚动需要的微秒数，数值越大，速度越慢

    //--- 日志 -------------------------------------------------------------------------------------

    fun Logi(msg: String) {
        if (!PagerConfig.isShowLog) return
        Log.i(TAG, msg)
    }

    fun Loge(msg: String) {
        if (!PagerConfig.isShowLog) return
        Log.e(TAG, msg)
    }
}
