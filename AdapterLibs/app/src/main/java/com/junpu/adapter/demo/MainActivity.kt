package com.junpu.adapter.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.junpu.adapter.demo.adapter.TestAdapter
import com.junpu.adapter.demo.adapter.TestItem
import com.junpu.adapter.demo.databinding.ActivityMainBinding
import com.junpu.adapter.demo.utils.binding
import com.junpu.toast.toast

class MainActivity : AppCompatActivity() {

    private val binding by binding<ActivityMainBinding>()

    private val adapter by lazy { TestAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter.apply {
                doOnItemClick { _, i ->
                    toast("click item : $i")
                }
            }
        }

        adapter.update(list)
    }

    private val list by lazy {
        mutableListOf<TestItem>().apply {
            for (i in 0 until 20) {
                add(TestItem("Title $i", "SubTitle $i"))
            }
        }
    }
}