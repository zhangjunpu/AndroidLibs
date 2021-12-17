package com.junpu.widget.sample.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.junpu.widget.dial.DialPlate
import com.junpu.widget.sample.R
import com.junpu.widget.sample.databinding.ActivityTvdialPlateBinding
import com.junpu.widget.sample.utils.binding

class TVDialPlateActivity : AppCompatActivity() {

    private val binding by binding<ActivityTvdialPlateBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.dialPlate.dialClick = object : DialPlate.OnDialClickListener {
            override fun click(dialNumber: String) {
                Toast.makeText(this@TVDialPlateActivity, dialNumber, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
