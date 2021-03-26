package com.junpu.utils

import java.util.*


/**
 * 日历工具类
 * @author junpu
 * @date 2019-07-24
 */

/**
 * 获取指定时间所在周的第一天（周1起）
 */
val Long.firstDayOfWeek: Calendar
    get() = calendarZeroPoint.apply {
        firstDayOfWeek = Calendar.MONDAY
        set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    }

/**
 * 获取指定时间所在周的最后一天（周1起）
 */
val Long.lastDayOfWeek: Calendar
    get() = calendarZeroPoint.apply {
        firstDayOfWeek = Calendar.MONDAY
        set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        add(Calendar.DAY_OF_YEAR, 1)
        add(Calendar.MILLISECOND, -1)
    }

/**
 * 获取指定日期下一周的第一天
 */
val Long.firstDayOfNextWeek: Calendar
    get() = firstDayOfWeek.apply {
        add(Calendar.WEEK_OF_YEAR, 1)
    }

/**
 * 获取指定日期上一周的第一天
 */
val Long.firstDayOfLastWeek: Calendar
    get() = firstDayOfWeek.apply {
        add(Calendar.WEEK_OF_YEAR, -1)
    }

/**
 * 根据时间戳获取几周前信息
 */
val Long.fewWeeksAgo: Int
    get() {
        val time = System.currentTimeMillis() - this
        val aWeek = ONE_WEEK_MILLIS
        return (time / aWeek).toInt()
    }

/**
 * 获取指定时间凌晨日历
 */
val Long.calendarZeroPoint: Calendar
    get() = Calendar.getInstance().also {
        it.timeInMillis = this.formatMilliseconds
        it.clearFromHour()
    }

/**
 * 获取某个时间戳的日历（精确到分钟）
 */
val Long.calendarByMinute: Calendar
    get() = Calendar.getInstance().apply {
        timeInMillis = formatMilliseconds
        clear(Calendar.SECOND)
        clear(Calendar.MILLISECOND)
    }

/**
 * 如果是10位10位则补全13位
 */
val Long.formatMilliseconds: Long
    get() = if (this.toString().length == 10) this * 1000 else this

/**
 * 获取指定时间戳的星期
 */
val Long.weekDay
    get() = this.calendarZeroPoint.run {
        when (get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "星期日"
            Calendar.SATURDAY -> "星期六"
            Calendar.FRIDAY -> "星期五"
            Calendar.THURSDAY -> "星期四"
            Calendar.WEDNESDAY -> "星期三"
            Calendar.TUESDAY -> "星期二"
            Calendar.MONDAY -> "星期一"
            else -> ""
        }
    }

/**
 * 将时，分，秒，以及毫秒值设置为0
 */
fun Calendar.clearFromHour() {
    set(Calendar.MILLISECOND, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.HOUR_OF_DAY, 0)
}

/**
 * 获取当前时间的log
 */
fun Calendar.log(flag: Boolean = true, tag: String? = "") {
    val year = get(Calendar.YEAR)
    val month = get(Calendar.MONTH) + 1
    val day = get(Calendar.DAY_OF_MONTH)
    val hour = get(Calendar.HOUR_OF_DAY)
    val minute = get(Calendar.MINUTE)
    val second = get(Calendar.SECOND)
    val millisecond = get(Calendar.MILLISECOND)
    if (flag) {
        println("$tag : year = ${year}-$month-$day $hour:$minute:$second.$millisecond")
    }
}

/**
 * 获取指定时间的日历
 * @param hour 0-23
 * @param minute 0-59
 * @param second 0-59
 */
fun getCalendar(hour: Int = 0, minute: Int = 0, second: Int = 0): Calendar =
    Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, second)
        clear(Calendar.MILLISECOND)
    }
