package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.widget.sample.databinding.ActivityDatePickerBinding
import com.junpu.widget.sample.utils.binding

/**
 * 时间Picker
 * @author junpu
 * @date 2020-01-14
 */
class DatePickerActivity : AppCompatActivity() {

    private val binding by binding<ActivityDatePickerBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.datePickerStart.year
    }
}