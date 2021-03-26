package com.junpu.tool.sample

import android.app.Application

/**
 * @author junpu
 * *
 * @time 2017/8/3 14:38
 */
class App : Application() {

    companion object {
        var application: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        application = this
//        DlogImpl.init(BuildConfig.DEBUG)
    }
}
