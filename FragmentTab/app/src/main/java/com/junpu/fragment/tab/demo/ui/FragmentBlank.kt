package com.junpu.fragment.tab.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.junpu.fragment.tab.demo.databinding.FragmentBlankBinding

const val ARGS_NAME = "args"

class FragmentBlank : Fragment() {

    private var binding: FragmentBlankBinding? = null

    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString(ARGS_NAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.textInfo?.text = name
        binding?.btnTest?.setOnClickListener {
            binding?.textTest?.text = "test change $name"
        }
    }
}
