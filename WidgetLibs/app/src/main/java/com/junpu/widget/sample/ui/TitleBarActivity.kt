package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.widget.sample.R
import com.junpu.widget.sample.databinding.ActivityTitlebarBinding
import com.junpu.widget.sample.utils.binding

/**
 *
 * @author Junpu
 * @time 2018/1/10 15:42
 */
class TitleBarActivity : AppCompatActivity() {

    private val binding by binding<ActivityTitlebarBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.titleBar.setTitle("TitleBar")
        binding.titleBar.setItemText(R.id.Next, "检查更新")
        binding.titleBar.setOnClickListener(R.id.Next) {
        }

    }
}