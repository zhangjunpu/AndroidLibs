@file:JvmName("AppHelper")

package com.junpu.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * App扩展类
 * @author junpu
 * @date 2019-06-29
 */

lateinit var app: Application

fun getStringResource(@StringRes stringId: Int) = app.getString(stringId)
fun getColorResource(@ColorRes colorId: Int) = app.getColorResource(colorId)
fun getColorStateListRes(@ColorRes colorId: Int) = app.getColorStateListRes(colorId)

fun Context.getColorResource(@ColorRes id: Int) = ContextCompat.getColor(this, id)
fun Context.getColorStateListRes(@ColorRes id: Int) = ContextCompat.getColorStateList(this, id)

fun Context.getApplicationMetaData(key: String): String? {
    val appInfo = packageManager?.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    return appInfo?.metaData?.getString(key)
}

fun Activity.getActivityMetaData(key: String): String? {
    val info = packageManager?.getActivityInfo(componentName, PackageManager.GET_META_DATA)
    return info?.metaData?.getString(key)
}

fun Context.getServiceMetaData(cls: Class<*>, key: String): String? {
    val componentName = ComponentName(this, cls)
    val info = packageManager?.getServiceInfo(componentName, PackageManager.GET_META_DATA)
    return info?.metaData?.getString(key)
}

fun Activity.getReceiverMetaData(cls: Class<*>, key: String): String? {
    val componentName = ComponentName(this, cls)
    val info = packageManager?.getReceiverInfo(componentName, PackageManager.GET_META_DATA)
    return info?.metaData?.getString(key)
}