@file:JvmName("LHelper")

package com.junpu.log

import android.util.Log

/**
 * Log工具类
 * @author junpu
 * @date 2019-12-29
 */

private const val HEADER = ": "
private const val HEADER_NEWLINE = ":\n"

/**
 * 最终输出结果
 */
internal fun Array<StackTraceElement>.stack(target: Any?, isNewLine: Boolean) = simpleLogResult?.let {
    val mark = if (isNewLine) HEADER_NEWLINE else HEADER
    "${it}${mark}${target}"
} ?: target.toString()

/**
 * 输出信息
 */
internal val Array<StackTraceElement>.logElement: StackTraceElement?
    get() {
        val first = first().className
        return firstOrNull { it.className != first }
    }

/**
 * 输出信息类名
 */
internal val Array<StackTraceElement>.simpleClassName: String?
    get() = logElement?.className?.let {
        it.substring(it.lastIndexOf(".") + 1)
    }

/**
 * 输出信息简单结果
 */
internal val Array<StackTraceElement>.simpleLogResult: String?
    get() = logElement?.run {
        val simpleClassName = className.let { it.substring(it.lastIndexOf(".") + 1) }
        val result = toString()
        val start = result.indexOf(simpleClassName)
        result.substring(start)
    }

/**
 * 打印堆栈错误信息
 */
fun Throwable.logStackTrace() = Log.e("System.err", Log.getStackTraceString(this))