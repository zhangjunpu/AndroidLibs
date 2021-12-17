package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.utils.launch
import com.junpu.widget.sample.databinding.ActivityMainBinding
import com.junpu.widget.sample.utils.binding

class MainActivity : AppCompatActivity() {

    private val binding by binding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.run {
            emptyView.setOnClickListener { launch<EmptyViewActivity>() }
            titleBar.setOnClickListener { launch<TitleBarActivity>() }
            tvAdbarview.setOnClickListener { launch<AdBarViewActivity>() }
            tvFloatView.setOnClickListener { launch<FloatViewActivity>() }
//            tvSlide.setOnClickListener { launch<SlideActivity>() }
            tvDialPlate.setOnClickListener { launch<PhoneDialActivity>() }
            tvDialPlateForTv.setOnClickListener { launch<TVDialPlateActivity>() }
            tvPopupWindow.setOnClickListener { launch<PopupWindowActivity>() }
        }
    }
}
