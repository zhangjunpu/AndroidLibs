package com.junpu.fragment.tab.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.junpu.fragment.tab.demo.databinding.FragmentEditBinding
import com.junpu.fragment.tab.demo.utils.MaxLengthWatcher

class EditFragment : Fragment() {

    private var binding: FragmentEditBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEditBinding.inflate(layoutInflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            val watcher = MaxLengthWatcher(editText, 10f).apply {
                doOnTextChange {
                    textInfo.text = "${it.toString().chineseLength}/${20}"
                }
            }
            editText.addTextChangedListener(watcher)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
