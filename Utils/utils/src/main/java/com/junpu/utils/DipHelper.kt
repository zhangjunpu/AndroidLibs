@file:JvmName("DipHelper")
@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.junpu.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import androidx.fragment.app.Fragment

/**
 * Dip Helper
 * @author junpu
 * @date 2020/8/27
 */

const val LDPI: Int = DisplayMetrics.DENSITY_LOW
const val MDPI: Int = DisplayMetrics.DENSITY_MEDIUM
const val HDPI: Int = DisplayMetrics.DENSITY_HIGH

const val TVDPI: Int = DisplayMetrics.DENSITY_TV
const val XHDPI: Int = DisplayMetrics.DENSITY_XHIGH
const val XXHDPI: Int = DisplayMetrics.DENSITY_XXHIGH
const val XXXHDPI: Int = DisplayMetrics.DENSITY_XXXHIGH

const val MAXDPI: Int = 0xfffe

//returns dip(dp) dimension value in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()

//return sp dimension value in pixels
fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()
fun Context.sp(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()

//converts px value into dip or sp
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density
fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

//the same for the views
inline fun View.dip(value: Int): Int = context.dip(value)
inline fun View.dip(value: Float): Int = context.dip(value)
inline fun View.sp(value: Int): Int = context.sp(value)
inline fun View.sp(value: Float): Int = context.sp(value)
inline fun View.px2dip(px: Int): Float = context.px2dip(px)
inline fun View.px2sp(px: Int): Float = context.px2sp(px)

//the same for Fragments
inline fun Fragment.dip(value: Int): Int = requireActivity().dip(value)
inline fun Fragment.dip(value: Float): Int = requireActivity().dip(value)
inline fun Fragment.sp(value: Int): Int = requireActivity().sp(value)
inline fun Fragment.sp(value: Float): Int = requireActivity().sp(value)
inline fun Fragment.px2dip(px: Int): Float = requireActivity().px2dip(px)
inline fun Fragment.px2sp(px: Int): Float = requireActivity().px2sp(px)

fun dip(value: Float): Int = app.dip(value)
fun dip(value: Int): Int = app.dip(value)
fun sp(value: Int): Int = app.sp(value)
fun sp(value: Float): Int = app.sp(value)
fun px2dip(px: Int): Float = app.px2dip(px)
fun px2sp(px: Int): Float = app.px2sp(px)

val Float.dip: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Int.dip: Int get() = toFloat().dip
val Float.sp: Int get() = (this * Resources.getSystem().displayMetrics.scaledDensity).toInt()
val Int.sp: Int get() = toFloat().sp
val Float.toDip: Float get() = this / Resources.getSystem().displayMetrics.density
val Int.toDip: Float get() = toFloat().toDip
val Float.toSp: Float get() = this / Resources.getSystem().displayMetrics.scaledDensity
val Int.toSp: Float get() = toFloat().toSp