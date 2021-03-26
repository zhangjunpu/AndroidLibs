package com.junpu.toast.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junpu.toast.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toast("hahaha")
    }
}