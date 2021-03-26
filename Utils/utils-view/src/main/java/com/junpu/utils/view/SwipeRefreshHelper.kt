@file:JvmName("SwipeRefreshHelper")

package com.junpu.utils.view

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * SwipeRefresh
 * @author junpu
 * @date 2021/2/23
 */

/**
 * 设置SwipeRefreshLayout立即刷新，并执行指定刷新动作
 */
fun SwipeRefreshLayout.doRefresh(d: () -> Unit) {
    isRefreshing = true
    d.invoke()
}
