package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.junpu.widget.dial.DialPlate
import com.junpu.widget.sample.R
import kotlinx.android.synthetic.main.activity_tvdial_plate.*

class TVDialPlateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvdial_plate)

        dialPlate.dialClick = object : DialPlate.OnDialClickListener {
            override fun click(dialNumber: String) {
                Toast.makeText(this@TVDialPlateActivity, dialNumber, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
