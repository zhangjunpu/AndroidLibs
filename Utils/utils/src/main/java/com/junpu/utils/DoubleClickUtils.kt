package com.junpu.utils

/**
 * 双击、多次点击退出
 * @author junpu
 * @date 2019-08-07
 */
object DoubleClickUtils {

    private var intervalTime: Long = 0

    /**
     * 双击操作
     */
    fun doubleClick(callback: () -> Unit): Boolean {
        return if (System.currentTimeMillis() - intervalTime > 1000) {
            intervalTime = System.currentTimeMillis()
            callback()
            false
        } else {
            true
        }
    }

    private var moreCount = 5 // 次数
    private var moreInterval = 0L

    /**
     * 点击多次触发
     */
    fun moreClick(count: Int = 5, callBack: ((count: Int) -> Unit)): Boolean {
        val curTime = System.currentTimeMillis()
        if (curTime - moreInterval > 1000) {
            moreCount = count
        }
        moreInterval = curTime
        moreCount--
        callBack.invoke(count)
        return moreCount == 0
    }
}
