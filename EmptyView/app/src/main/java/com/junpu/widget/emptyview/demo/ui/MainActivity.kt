package com.junpu.widget.emptyview.demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.utils.launch
import com.junpu.widget.emptyview.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            btnEmptyView.setOnClickListener {
                launch<EmptyViewActivity>()
            }
            btnSimpleEmptyView.setOnClickListener {
                launch<SimpleEmptyViewActivity>()
            }
        }
    }
}