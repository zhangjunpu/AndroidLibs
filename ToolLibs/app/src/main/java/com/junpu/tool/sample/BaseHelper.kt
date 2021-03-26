@file:JvmName("BaseHelper")

package com.junpu.tool.sample

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Binder
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import java.io.Serializable

/**
 * 基础扩展类
 * @author junpu
 * @time 2017/11/7 23:09
 */

fun Context.launch(cls: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, cls)
    bundle?.let { intent.putExtras(bundle) }
    startActivity(intent)
}

fun androidx.fragment.app.Fragment.launch(cls: Class<*>, bundle: Bundle? = null) {
    context?.launch(cls, bundle)
}

fun androidx.fragment.app.Fragment.isPortrait(): Boolean =
    activity?.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

fun Context.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
    LayoutInflater.from(this).inflate(resource, root, attachToRoot)

fun Context.color(@ColorRes id: Int): Int {
    return if (VERSION.SDK_INT >= VERSION_CODES.M) {
        resources.getColor(id, null)
    } else {
        resources.getColor(id)
    }
}

fun Context.dp2px(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun Context.dp2px(dp: Int): Int = dp2px(dp.toFloat())

fun androidx.fragment.app.Fragment.dp2px(dp: Float): Int = context?.dp2px(dp) ?: 0
fun androidx.fragment.app.Fragment.dp2px(dp: Int): Int = context?.dp2px(dp) ?: 0

fun View.dp2px(dp: Float): Int = context?.dp2px(dp) ?: 0
fun View.dp2px(dp: Int): Int = context?.dp2px(dp) ?: 0

fun dp2px(dp: Float): Int = (dp * Resources.getSystem().displayMetrics.density + 0.5).toInt()
fun dp2px(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density + 0.5).toInt()

/**
 * 获取Intent实例，此Intent清掉目标之上的Activity，并走目标的onNewIntent()方法
 */
fun Context.getNewIntent(cls: Class<*>): Intent {
    val intent = Intent(this, cls)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // 清掉目标Activity和其之上的Activity，并重新创建目标Activity
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) // 不会清掉目标Activity，而是走onNewIntent()方法
    return intent
}

fun postDelay(r: Runnable, delayMillis: Long) = Handler().postDelayed(r, delayMillis)
fun postDelay(delayMillis: Long = 100, r: () -> Unit) = Handler().postDelayed(r, delayMillis)

/**
 * 重试
 */
fun postRetry(retryTimes: Int, delayMillis: Long = 100, retry: (retryTimes: Int) -> Unit) {
    var retryIndex = retryTimes
    retryIndex--
    if (retryIndex > 0) postDelay(delayMillis) { retry(retryIndex) }
}

fun Context.getSharedPreferences(name: String): SharedPreferences {
    return applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)
}

fun <T> SharedPreferences.get(key: String, defaultValue: T): T {
    val value = when (defaultValue) {
        is String -> getString(key, defaultValue)
        is Boolean -> getBoolean(key, defaultValue)
        is Int -> getInt(key, defaultValue)
        is Long -> getLong(key, defaultValue)
        is Float -> getFloat(key, defaultValue)
        else -> defaultValue
    }
    return value as? T ?: defaultValue
}