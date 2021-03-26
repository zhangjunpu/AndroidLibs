@file:JvmName("StringHelper")

package com.junpu.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment

/**
 * String字符串工具类
 * @author junpu
 * @date 2019-08-10
 */

fun SpannableStringBuilder.appendString(text: String) = apply {
    append(text)
}

fun SpannableStringBuilder.addSpan(
    what: Any,
    start: Int,
    length: Int,
    flag: Int = SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
) = apply {
    setSpan(what, start, start + length, flag)
}

fun SpannableStringBuilder.addSpan(
    colorResId: Int,
    start: Int,
    length: Int,
    flag: Int = SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
) = apply {
    setSpan(ForegroundColorSpan(getColorResource(colorResId)), start, start + length, flag)
}

/**
 * 返回当前数值字符串形式的长度
 */
fun Int.length(): Int = toString().length

fun Context.foregroundColorSpan(resId: Int) = ForegroundColorSpan(getColorResource(resId))
fun Context.foregroundColorSpan(color: String) = ForegroundColorSpan(Color.parseColor(color))
fun Fragment.foregroundColorSpan(resId: Int) = ForegroundColorSpan(getColorResource(resId))
fun Fragment.foregroundColorSpan(color: String) = ForegroundColorSpan(Color.parseColor(color))
fun Dialog.foregroundColorSpan(resId: Int) = context.foregroundColorSpan(resId)
fun Dialog.foregroundColorSpan(color: String) = context.foregroundColorSpan(color)

fun SpannableString.addSpan(
    what: Any,
    start: Int,
    length: Int,
    flag: Int = SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
) = apply {
    setSpan(what, start, start + length, flag)
}

fun SpannableString.addSpan(
    colorResId: Int,
    start: Int,
    length: Int,
    flag: Int = SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
) = apply {
    setSpan(ForegroundColorSpan(getColorResource(colorResId)), start, start + length, flag)
}

fun CharSequence.toSpannable(): SpannableString = SpannableString.valueOf(this)
fun CharSequence.toSpannable(
    what: Any,
    start: Int,
    length: Int,
    flag: Int = SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
) = toSpannable().addSpan(what, start, length, flag)

fun spannableOf(resId: Int): SpannableString = SpannableString.valueOf(getStringResource(resId))