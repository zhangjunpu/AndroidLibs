package com.junpu.widget.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junpu.widget.sample.databinding.ActivityAdBarViewBinding
import com.junpu.widget.sample.utils.binding
import java.util.*

/**
 *
 * @author chengxiaobo
 * @time 2018/3/7
 */
class AdBarViewActivity : AppCompatActivity() {

    private val binding by binding<ActivityAdBarViewBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //设置集合
        val items = ArrayList<String>()
        items.add("这是第1个")
        items.add("这是第2个")
        items.add("这是很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的第3个")
        items.add("这是第4个")
        items.add("这是第5个")
        items.add("这是第6个")
        items.add("这是第7个")

        binding.mvBar1.startWithList(items)

        binding.mvBar2.post {
            binding.mvBar2.initData(listOf("一项的时候"))
            binding.mvBar2.postDelayed({
                binding.mvBar2.initData(items)
            }, 5000)
        }
    }
}
