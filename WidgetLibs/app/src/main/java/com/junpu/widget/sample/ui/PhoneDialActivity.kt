package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.widget.phonedial.PhoneDialView.OnDialNumberListener
import com.junpu.widget.sample.databinding.ActivityPhoneDialBinding
import com.junpu.widget.sample.utils.binding

class PhoneDialActivity : AppCompatActivity() {

    private val binding by binding<ActivityPhoneDialBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding.dialPlate.dialClick = object : DialPlate.OnDialClickListener {
//            override fun click(dialNumber: String) {
//                Toast.makeText(this@PhoneDialActivity, dialNumber, Toast.LENGTH_SHORT).show()
//            }
//        }

        binding.dialPlate.setOnDialNumberListener(object : OnDialNumberListener {
            override fun onDialNumber(number: String?) {
                println("number = $number")
            }
        })
    }
}
