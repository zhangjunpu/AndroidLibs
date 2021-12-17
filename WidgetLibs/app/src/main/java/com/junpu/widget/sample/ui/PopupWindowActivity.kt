package com.junpu.widget.sample.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.junpu.toast.toast
import com.junpu.utils.dip
import com.junpu.widget.popupwindow.CustomPopup
import com.junpu.widget.popupwindow.ListPopup
import com.junpu.widget.sample.R
import com.junpu.widget.sample.databinding.ActivityPopupwindowBinding
import com.junpu.widget.sample.databinding.PopupDeleteBinding
import com.junpu.widget.sample.utils.binding

/**
 * PopupWindow
 * @author junpu
 * @date 2019-07-04
 */
class PopupWindowActivity : AppCompatActivity() {

    private val binding by binding<ActivityPopupwindowBinding>()

    private var listPopup: ListPopup? = null
    private var customPopup: CustomPopup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.subject.setOnClickListener {
            showListPopup(it)
        }
        binding.mode.setOnClickListener {
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
