package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.widget.phonedial.PhoneDialView.OnDialNumberListener
import com.junpu.widget.sample.R
import kotlinx.android.synthetic.main.activity_phone_dial.*

class PhoneDialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_dial)

//        dialPlate.dialClick = object : DialPlate.OnDialClickListener {
//            override fun click(dialNumber: String) {
//                Toast.makeText(this@PhoneDialActivity, dialNumber, Toast.LENGTH_SHORT).show()
//            }
//        }

        dialPlate?.setOnDialNumberListener(object : OnDialNumberListener {
            override fun onDialNumber(number: String?) {
                println("number = $number")
            }
        })
    }
}
