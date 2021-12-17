package com.junpu.tool.sample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.tool.sample.databinding.ActivityMainBinding
import com.junpu.utils.launch

/**
 *
 * @author Junpu
 * @time 2018/1/3 15:05
 */
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.imagePicker.setOnClickListener {
            launch<ImagePickerActivity>()
        }
        binding.fragmentTab.setOnClickListener {
            launch<TabActivity>()
        }
        binding.system.setOnClickListener {
            launch<DeviceInfoActivity>()
        }
    }
}