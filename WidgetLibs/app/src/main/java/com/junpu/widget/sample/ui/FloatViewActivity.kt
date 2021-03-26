package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.junpu.widget.sample.R
import kotlinx.android.synthetic.main.activity_float_view.*

class FloatViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_view)

        floatView.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }

        ChildFloatView.setOnClickListener {
            Toast.makeText(this, "ChildClick", Toast.LENGTH_SHORT).show()
        }
    }
}
