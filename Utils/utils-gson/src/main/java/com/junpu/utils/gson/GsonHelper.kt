@file:JvmName("GsonHelper")

package com.junpu.utils.gson

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Gson工具类
 * @author junpu
 * @date 2019-12-13
 */

val gson by lazy { Gson() }

/**
 * Gson fromJson, convert string to object by Class.
 */
inline fun <reified T> String.fromJson(): T? {
    return try {
        gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        Log.e("System.err", e.stackTraceToString())
        null
    }
}

/**
 * Gson fromJson, convert string to object by TypeT.
 */
inline fun <reified T> String.fromJsonType(): T? {
    return try {
        gson.fromJson(this, object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        Log.e("System.err", e.stackTraceToString())
        null
    }
}

/**
 * Gson toJson, convert object to string;
 */
fun Any.toJson(): String? {
    return try {
        gson.toJson(this)
    } catch (e: Exception) {
        Log.e("System.err", e.stackTraceToString())
        null
    }
}
