package com.junpu.toast.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.toast.demo.databinding.ActivityMainBinding
import com.junpu.toast.toast

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnToast.setOnClickListener {
            toast("hahaha")
        }
    }
}