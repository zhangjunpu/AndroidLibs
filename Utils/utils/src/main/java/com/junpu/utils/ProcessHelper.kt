@file:JvmName("ProcessHelper")
package com.junpu.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Process

/**
 * @author : Junpu
 * @data : 2022/8/3
 */

/**
 * 是否是主进程
 * @author Junpu
 * @date 2022/8/3
 */
fun isMainProcess(context: Context): Boolean {
    val pid = Process.myPid()
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (appProcess in activityManager.runningAppProcesses) {
        if (appProcess.pid == pid) {
            return context.applicationInfo.packageName == appProcess.processName
        }
    }
    return false
}