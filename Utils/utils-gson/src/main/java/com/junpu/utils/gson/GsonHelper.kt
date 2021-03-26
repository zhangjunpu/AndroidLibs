@file:JvmName("GsonHelper")

package com.junpu.utils.gson

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Gson工具类
 * @author junpu
 * @date 2019-12-13
 */

/**
 * Gson fromJson
 */
fun <T> String.fromJson(cls: Class<T>?): T? {
    return try {
        Gson().fromJson(this, cls)
    } catch (e: Exception) {
        e.logStackTrace()
        null
    }
}

/**
 * Gson fromJson
 */
fun <T> String.fromJson(): T? {
    return try {
        Gson().fromJson(this, object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        e.logStackTrace()
        null
    }
}

/**
 * Gson fromJson
 */
fun <T> String.fromJsonType(type: Type): T? {
    return try {
        Gson().fromJson(this, type)
    } catch (e: Exception) {
        e.logStackTrace()
        null
    }
}

/**
 * Gson toJson
 */
fun Any.toJson(): String? {
    return try {
        Gson().toJson(this)
    } catch (e: Exception) {
        e.logStackTrace()
        null
    }
}

private fun Throwable.logStackTrace() = Log.e("System.err", stackTraceToString())
