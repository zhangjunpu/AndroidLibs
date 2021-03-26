package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.widget.sample.R
import kotlinx.android.synthetic.main.activity_titlebar.*

/**
 *
 * @author Junpu
 * @time 2018/1/10 15:42
 */
class TitleBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_titlebar)
        initTitleBar()
    }

    private fun initTitleBar() {
//        val titleBar = findViewById<TitleBar>(R.id.titleBar)
        titleBar.setTitle("TitleBar")
        titleBar.setItemText(R.id.Next, "检查更新")
        titleBar.setOnClickListener(R.id.Next) {
        }
    }

}