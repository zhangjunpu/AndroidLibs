package com.junpu.adapter.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.junpu.adapter.demo.databinding.ActivityMainBinding
import com.junpu.adapter.demo.ui.*
import com.junpu.adapter.demo.utils.binding
import com.junpu.utils.launch

class MainActivity : AppCompatActivity() {

    private val binding by binding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            btnNormal.setOnClickListener {
                gotoNext(PAGE_NORMAL)
            }
            btnHeader.setOnClickListener {
                gotoNext(PAGE_HEADER)
            }
            btnSingle.setOnClickListener {
                gotoNext(PAGE_SINGLE)
            }
            btnMultiple.setOnClickListener {
                gotoNext(PAGE_MULTIPLE)
            }
        }
    }

    private fun gotoNext(arg: String) {
        launch<ListActivity>(bundleOf(ARG_PAGE to arg))
    }
}