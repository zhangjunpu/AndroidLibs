package com.junpu.tool.sample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.junpu.fragment.tab.FragmentTabManager
import com.junpu.tool.sample.R
import com.junpu.tool.sample.fragment.ARGS_NAME
import com.junpu.tool.sample.fragment.FragmentBlank
import kotlinx.android.synthetic.main.activity_tab.*

/**
 *
 * @author junpu
 * @date 2019-11-22
 */
class TabActivity : AppCompatActivity() {

    private var tabManager: FragmentTabManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        initView()
    }

    private fun initView() {
        tabManager = FragmentTabManager(this, supportFragmentManager, R.id.layoutContent).apply {
            setBackStack(true)
            addTab(FragmentBlank::class.java, "fragment1", bundleOf(ARGS_NAME to "tab1"))
            addTab(FragmentBlank::class.java, "fragment2", bundleOf(ARGS_NAME to "tab2"))
            addTab(FragmentBlank::class.java, "fragment3", bundleOf(ARGS_NAME to "tab3"))
            setSwitchListener { from, to -> println("switch ${from?.tag} ---> ${to?.tag}") }
        }
        btn1.setOnClickListener { switch(0) }
        btn2.setOnClickListener { switch(1) }
        btn3.setOnClickListener { switch(2) }
    }

    private fun switch(position: Int) {
        tabManager?.switchTo(position)
    }

    override fun onBackPressed() {
        if (tabManager?.backStack() != true) super.onBackPressed()
    }
}