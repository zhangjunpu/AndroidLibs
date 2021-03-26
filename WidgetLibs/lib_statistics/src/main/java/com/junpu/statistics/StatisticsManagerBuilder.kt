package com.junpu.statistics

import android.content.Context

/**
 * 统计管理类 Builder
 *
 * @author chengxiaobo
 * @time 2018/4/10 18:18
 */
class StatisticsManagerBuilder(context: Context) {

    private val context = context

    fun add(bean: StatisticsInterface): StatisticsManagerBuilder {
        StatisticsManager.add(bean)
        return this
    }

    fun create(): StatisticsManager {
        StatisticsManager.init(context)
        return StatisticsManager
    }

}