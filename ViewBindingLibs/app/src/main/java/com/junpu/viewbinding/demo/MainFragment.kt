package com.junpu.viewbinding.demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.junpu.log.L
import com.junpu.viewbinding.binding
import com.junpu.viewbinding.demo.databinding.FragmentMainBinding

/**
 * @author junpu
 * @date 2022/5/27
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by binding<FragmentMainBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textInfo.text = "Hello ViewBinding Fragment"
    }

    override fun onDestroy() {
        super.onDestroy()
        L.vv()
    }

    fun finalize() {
        L.vv()
    }
}