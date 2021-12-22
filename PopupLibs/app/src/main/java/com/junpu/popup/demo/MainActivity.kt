package com.junpu.popup.demo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.junpu.popup.CustomPopup
import com.junpu.popup.ListPopup
import com.junpu.popup.demo.databinding.ActivityPopupwindowBinding
import com.junpu.popup.demo.databinding.PopupDeleteBinding
import com.junpu.toast.toast
import com.junpu.utils.dip

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPopupwindowBinding.inflate(layoutInflater) }

    private var listPopup: ListPopup? = null
    private var customPopup: CustomPopup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btn1.setOnClickListener {
            showListPopup(it)
        }
        binding.btn2.setOnClickListener {
            showCustomPopup(it)
        }
    }

    /**
     * ListPopup
     */
    private fun showListPopup(showView: View?) {
        if (listPopup == null) {
            val data = arrayListOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
            val adapter = ArrayAdapter(this, R.layout.simple_list_item, data)
            listPopup = ListPopup(this, CustomPopup.DIRECTION_NONE, adapter).apply {
                val width = dip(150)
                val maxHeight = ViewGroup.LayoutParams.WRAP_CONTENT
                create(width, maxHeight) { adapterView, view, position, id ->
                    toast("Item $position")
                    dismiss()
                }
                setOnDismissListener {
                    //                    toast("pop dismiss")
                }
                setAnimStyle(CustomPopup.ANIM_GROW_FROM_CENTER)
                setPreferredDirection(CustomPopup.DIRECTION_BOTTOM)
            }
        }
        listPopup?.show(showView)
    }

    /**
     * CustomPopup
     */
    private fun showCustomPopup(showView: View?) {
        val binding = PopupDeleteBinding.inflate(layoutInflater).apply {
            btnSubmit.setOnClickListener {
                toast("确定")
                customPopup?.dismiss()
            }
            btnCancel.setOnClickListener {
                customPopup?.dismiss()
            }
        }
        customPopup = CustomPopup(this).apply {
            setContentView(binding.root)
            setAnimStyle(CustomPopup.ANIM_GROW_FROM_CENTER)
            setPreferredDirection(CustomPopup.DIRECTION_TOP)
            show(showView)
        }
    }

}