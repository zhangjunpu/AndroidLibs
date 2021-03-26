package com.junpu.gopermissions.demo

import android.app.Application
import com.junpu.log.L

/**
 *
 * @author junpu
 * @date 2020/7/13
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        L.logEnable = BuildConfig.DEBUG
    }
}