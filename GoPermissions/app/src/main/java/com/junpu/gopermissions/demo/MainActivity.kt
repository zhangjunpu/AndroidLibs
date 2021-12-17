package com.junpu.gopermissions.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.gopermissions.demo.databinding.ActivityMainBinding

/**
 *
 * @author junpu
 * @date 2020/7/13
 */
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}