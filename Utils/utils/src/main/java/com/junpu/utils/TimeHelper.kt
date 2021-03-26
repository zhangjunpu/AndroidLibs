@file:JvmName("TimeHelper")

package com.junpu.utils

import android.text.format.DateFormat
import java.util.*

/**
 * 日期时间工具类
 *
 * <br>
 * y：年
 * M：月
 * d：日
 * h：时（12小时制，0-12）
 * H：时（24小时制，0-23）
 * m：分
 * s：秒
 * S：毫秒
 * E：星期几
 * a：上下午标识
 * </br>
 *
 * @author junpu
 * @date 2019-07-03
 */

const val ONE_SECOND_MILLIS = 1000 // 1秒
const val ONE_MINUTE_MILLIS = 60 * ONE_SECOND_MILLIS // 1分钟
const val ONE_HOUR_MILLIS = 60 * ONE_MINUTE_MILLIS // 1小时
const val ONE_DAY_MILLIS = 24 * ONE_HOUR_MILLIS // 1天
const val ONE_WEEK_MILLIS = 7 * ONE_DAY_MILLIS // 1周

/**
 * Date格式化
 */
fun Date.format(format: CharSequence): CharSequence = DateFormat.format(format, this)

val Date.formatDateTime
    get() = format("yyyy-MM-dd HH:mm")

val Date.formatDate
    get() = format("yyyy-MM-dd")

val Date.formatDateWithoutYear
    get() = format("MM-dd")

val Date.formatDateChinese
    get() = format("yyyy年MM月dd日")

val Date.formatDateChineseWithoutYear
    get() = format("MM月dd日")

val Date.formatTime
    get() = format("HH:mm")

val Date.formatWeek
    get() = format("E")

/**
 * 时间戳格式化
 */
fun Long.format(format: CharSequence): CharSequence = Date(this).format(format)

val Long.formatDateTime
    get() = Date(this).formatDateTime

val Long.formatDate
    get() = Date(this).formatDate

val Long.formatDateWithoutYear
    get() = Date(this).formatDateWithoutYear

val Long.formatDateChinese
    get() = Date(this).formatDateChinese

val Long.formatDateChineseWithoutYear
    get() = Date(this).formatDateChineseWithoutYear

val Long.formatTime
    get() = Date(this).formatTime

val Long.formatWeek
    get() = Date(this).formatWeek

/**
 * 非空格式化
 */
fun Long?.formatNotNull(format: CharSequence): CharSequence =
    if (this == null || this == 0L) "" else format(format)

val Long?.formatDateTimeNotNull
    get() = if (this == null || this == 0L) "" else formatDateTime

val Long?.formatDateNotNull
    get() = if (this == null || this == 0L) "" else formatDate

val Long?.formatDateWithoutYearNotNull
    get() = if (this == null || this == 0L) "" else formatDateWithoutYear

val Long?.formatDateChineseNotNull
    get() = if (this == null || this == 0L) "" else formatDateChinese

val Long?.formatDateChineseWithoutYearNotNull
    get() = if (this == null || this == 0L) "" else formatDateChineseWithoutYear

val Long?.formatTimeNotNull
    get() = if (this == null || this == 0L) "" else formatTime

val Long?.formatWeekNotNull
    get() = if (this == null || this == 0L) "" else formatWeek

/**
 * 格式化时间
 */
fun formatDuration(millisecond: Long): String {
    val totalSeconds = millisecond / 1000
    val days = totalSeconds / 60 / 60 / 24
    val hours = totalSeconds / 60 / 60 % 24
    val minutes = totalSeconds / 60 % 60
    val seconds = totalSeconds % 60
    return when {
        days > 0 -> String.format("%dT, %02d:%02d:%02d", days, hours, minutes, seconds)
        hours > 0 -> String.format("%02d:%02d:%02d", hours, minutes, seconds)
        else -> String.format("%02d:%02d", minutes, seconds)
    }
}

/**
 * 格式化时间
 */
fun formatVoiceDuration(millisecond: Long): String {
    val seconds = millisecond / 1000
    return String.format("%d\"", seconds)
}

/**
 * 格式化日期，今天、昨天
 */
val Long.formatDateTodayOrYesterday: CharSequence
    get() {
        val todayZero = System.currentTimeMillis().calendarZeroPoint.timeInMillis // 今天零点
        val yesterdayZero = todayZero - ONE_DAY_MILLIS // 昨天零点
        val tomorrowZero = todayZero + ONE_DAY_MILLIS // 明天凌晨
        return when {
            this >= tomorrowZero -> "明天"
            this in todayZero until tomorrowZero -> "今天"
            this in yesterdayZero until todayZero -> "昨天"
            else -> this.formatDateChineseWithoutYear
        }
    }