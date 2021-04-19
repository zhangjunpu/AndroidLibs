@file:JvmName("BaseHelper")

package com.junpu.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * 基础扩展类
 * @author junpu
 * @time 2017/11/7 23:09
 */

inline fun <reified T> Context.launch(bundle: Bundle? = null, vararg flags: Int) {
    val intent = Intent(this, T::class.java).apply {
        flags.forEach { addFlags(it) }
        bundle?.let { putExtras(bundle) }
    }
    startActivity(intent)
}

inline fun <reified T> Fragment.launch(bundle: Bundle? = null, vararg flags: Int) {
    context?.launch<T>(bundle, *flags)
}

inline fun <reified T> Activity.launchForResult(
    requestCode: Int,
    args: Bundle? = null,
    vararg flags: Int
) {
    val intent = Intent(this, T::class.java).apply {
        flags.forEach { addFlags(it) }
        args?.let { putExtras(args) }
    }
    startActivityForResult(intent, requestCode)
}

inline fun <reified T> Fragment.launchForResult(
    requestCode: Int,
    args: Bundle? = null,
    vararg flags: Int
) {
    val intent = Intent(context, T::class.java).apply {
        flags.forEach { addFlags(it) }
        args?.let { putExtras(args) }
    }
    startActivityForResult(intent, requestCode)
}

fun Fragment.isPortrait(): Boolean =
    activity?.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

inline val Context.layoutInflater: LayoutInflater get() = LayoutInflater.from(this)
inline val View.layoutInflater: LayoutInflater get() = context.layoutInflater

fun Context.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
    layoutInflater.inflate(resource, root, attachToRoot)

fun Fragment.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
    layoutInflater.inflate(resource, root, attachToRoot)

fun View.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
    layoutInflater.inflate(resource, root, attachToRoot)

fun ViewGroup.inflate(resource: Int, attachToRoot: Boolean = false): View =
    layoutInflater.inflate(resource, this, attachToRoot)

inline fun Fragment.runOnUiThread(crossinline f: () -> Unit) {
    requireActivity().runOnUiThread { f() }
}

/**
 * 获取Intent实例，此Intent清掉目标之上的Activity，并走目标的onNewIntent()方法
 */
inline fun <reified T> Context.getNewIntent() = Intent(this, T::class.java).newIntent

val Intent.newIntent: Intent
    get() = apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // 清掉目标Activity和其之上的Activity，并重新创建目标Activity
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) // 不会清掉目标Activity，而是走onNewIntent()方法
    }

inline fun postDelay(
    delayMillis: Long = 100,
    looper: Looper = Looper.getMainLooper(),
    crossinline r: () -> Unit
) = Handler(looper).postDelayed({ r() }, delayMillis)

/**
 * 重试
 */
inline fun postRetry(
    retryTimes: Int,
    delayMillis: Long = 100,
    crossinline retry: (retryTimes: Int) -> Unit
) {
    var retryIndex = retryTimes
    retryIndex--
    if (retryIndex > 0) postDelay(delayMillis) { retry(retryIndex) }
}
