package com.junpu.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.utils.encrypt.AesUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val key = "zhangjunpu19890311090012"
        val text = "欢迎来到chacuo.net"
        val result = AesUtils.encrypt(text, key)
        println(result) // aqrq0w/orfyXowAEBW+NRn3mmnb5VdEwbky7Jt30hFQ=

        val a = AesUtils.decrypt(result, key)
        println(a) // 欢迎来到chacuo.net

        val test: String? = null
        try {
            test!!.toString()
        } catch (e: Exception) {
            e.logStackTrace()
        }
    }
}