package com.junpu.utils

import android.util.Log

/**
 *
 * @author junpu
 * @date 2021/2/22
 */

fun Throwable.logStackTrace() {
    Log.e("System.err", stackTraceToString())
}