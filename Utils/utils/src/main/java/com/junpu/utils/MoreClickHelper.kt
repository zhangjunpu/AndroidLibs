package com.junpu.utils

/**
 * 双击事件、多击事件
 * @author junpu
 * @date 2019-08-07
 */

private var intervalCount = 5 // 次数
private var intervalTime = 0L

/**
 * 点击固定次数后触发某些事件，[count] 点击几次触发，[delayClickOnce] 是否延迟点击一次，[block] 触发块
 */
fun moreClick(
    count: Int = 5,
    delayClickOnce: Boolean = false,
    block: (count: Int) -> Unit
): Boolean {
    val curTime = System.currentTimeMillis()
    if (curTime - intervalTime > 1000) {
        intervalCount = count
        if (!delayClickOnce) intervalCount--
    } else {
        intervalCount--
    }
    intervalTime = curTime
    if (intervalCount in 0..count && (!delayClickOnce || intervalCount != count)) {
        block(intervalCount)
    }
    return intervalCount == 0
}

/**
 * 双击触发
 */
fun doubleClick(block: () -> Unit) = moreClick(2) {
    if (it > 0) block()
}
