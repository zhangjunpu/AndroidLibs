package com.junpu.viewbinding.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.viewbinding.bindingOnly
import com.junpu.viewbinding.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by bindingOnly<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}