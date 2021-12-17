package com.junpu.adapter.demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.junpu.adapter.demo.adapter.HeaderAdapter
import com.junpu.adapter.demo.adapter.MultipleAdapter
import com.junpu.adapter.demo.adapter.SingleAdapter
import com.junpu.adapter.demo.adapter.TestAdapter
import com.junpu.adapter.demo.databinding.ActivityListBinding
import com.junpu.adapter.demo.model.AdapterItem
import com.junpu.adapter.demo.utils.binding
import com.junpu.toast.toast

const val ARG_PAGE = "page"

const val PAGE_NORMAL = "normal"
const val PAGE_HEADER = "header"
const val PAGE_SINGLE = "single"
const val PAGE_MULTIPLE = "multiple"

/**
 * RecyclerView
 * @author junpu
 * @date 2021/12/17
 */
class ListActivity : AppCompatActivity() {

    private val binding by binding<ActivityListBinding>()

    private val testAdapter by lazy { TestAdapter() }
    private val singleAdapter by lazy { SingleAdapter() }
    private val multipleAdapter by lazy { MultipleAdapter() }
    private val headerAdapter by lazy { HeaderAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val page = intent.getStringExtra(ARG_PAGE)
        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = when (page) {
                PAGE_HEADER -> headerAdapter
                PAGE_SINGLE -> singleAdapter
                PAGE_MULTIPLE -> multipleAdapter
                else -> testAdapter
            }.apply {
                doOnItemClick { _, i ->
                    toast(getItem(i)?.title)
                }
                update(list)
            }
        }
    }

    private val list by lazy {
        mutableListOf<AdapterItem>().apply {
            for (i in 0 until 20) {
                add(AdapterItem("Title $i", "SubTitle $i"))
            }
        }
    }
}