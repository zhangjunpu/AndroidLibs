package com.junpu.log

import android.util.Log

/**
 * Log类
 * @author junpu
 * @date 2019-12-29
 */
object L {

    /**
     * 以ClassName为Tag的集合
     */
    private val tagClassSet = hashSetOf<String>()

    /**
     * Log输出Tag
     */
    @JvmStatic val tag: String = L::class.java.simpleName

    /**
     * 日志开关
     */
    @JvmStatic var logEnable = true

    @JvmStatic fun v(o: Any? = "") = output(Log.VERBOSE, o)
    @JvmStatic fun d(o: Any? = "") = output(Log.DEBUG, o)
    @JvmStatic fun i(o: Any? = "") = output(Log.INFO, o)
    @JvmStatic fun w(o: Any? = "") = output(Log.WARN, o)
    @JvmStatic fun e(o: Any? = "") = output(Log.ERROR, o)
    @JvmStatic fun a(o: Any? = "") = output(Log.ASSERT, o)

    @JvmStatic fun vv(o: Any? = "") = output(Log.VERBOSE, o, true)
    @JvmStatic fun dd(o: Any? = "") = output(Log.DEBUG, o, true)
    @JvmStatic fun ii(o: Any? = "") = output(Log.INFO, o, true)
    @JvmStatic fun ww(o: Any? = "") = output(Log.WARN, o, true)
    @JvmStatic fun ee(o: Any? = "") = output(Log.ERROR, o, true)
    @JvmStatic fun aa(o: Any? = "") = output(Log.ASSERT, o, true)

    @JvmStatic fun vvv(o: Any? = "") = output(Log.VERBOSE, o, true, isNewLine = true)
    @JvmStatic fun ddd(o: Any? = "") = output(Log.DEBUG, o, true, isNewLine = true)
    @JvmStatic fun iii(o: Any? = "") = output(Log.INFO, o, true, isNewLine = true)
    @JvmStatic fun www(o: Any? = "") = output(Log.WARN, o, true, isNewLine = true)
    @JvmStatic fun eee(o: Any? = "") = output(Log.ERROR, o, true, isNewLine = true)
    @JvmStatic fun aaa(o: Any? = "") = output(Log.ASSERT, o, true, isNewLine = true)

    /**
     * 打印System.out.println()信息
     */
    @JvmStatic
    fun out(o: Any?) {
        if (logEnable) println(o)
    }

    /**
     * 添加本类的Log的Tag为类名
     */
    @JvmStatic
    fun addTag() = Exception().stackTrace.simpleClassName?.let {
        tagClassSet.add(it)
    }

    /**
     * 移除本类的Log的TAG标记
     */
    @JvmStatic
    fun removeTag() = Exception().stackTrace.simpleClassName?.let {
        if (tagClassSet.contains(it)) tagClassSet.remove(it) else false
    }

    /**
     * 输出
     */
    private fun output(level: Int, o: Any?, isStack: Boolean = false, isNewLine: Boolean = false) {
        if (logEnable) {
            val stack = Exception().stackTrace
            val className = stack.simpleClassName
            val logBody = if (isStack) stack.stack(o, isNewLine) else o.toString()
            log(level, checkTag(className), logBody)
        }
    }

    /**
     * 检查是否要用类名为tag
     */
    private fun checkTag(cls: String?): String {
        cls ?: return tag
        return if (tagClassSet.contains(cls)) cls else tag
    }

    private fun log(level: Int, tag: String, message: String) {
        when (level) {
            Log.VERBOSE -> Log.v(tag, message)
            Log.DEBUG -> Log.d(tag, message)
            Log.INFO -> Log.i(tag, message)
            Log.WARN -> Log.w(tag, message)
            Log.ERROR -> Log.e(tag, message)
            Log.ASSERT -> Log.d(tag, message)
        }
    }
}

