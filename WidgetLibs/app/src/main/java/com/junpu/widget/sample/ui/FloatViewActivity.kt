package com.junpu.widget.sample.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.junpu.widget.sample.databinding.ActivityFloatViewBinding
import com.junpu.widget.sample.utils.binding

class FloatViewActivity : AppCompatActivity() {

    private val binding by binding<ActivityFloatViewBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.floatView.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }

//        binding.floatChild.setOnClickListener {
            Toast.makeText(this, "chick float child", Toast.LENGTH_SHORT).show()
//        }
    }
}
