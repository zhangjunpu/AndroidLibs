package com.junpu.dialog

import android.content.Context
import android.content.DialogInterface
import android.widget.NumberPicker
import com.junpu.dialog.databinding.DialogNumberpickerBinding

/**
 * 列表对话框
 */
class NumberPickerDialog(context: Context) : BaseDialog(context, R.style.Dialog_Theme_Bottom) {

    private val binding by lazy {
        DialogNumberpickerBinding.inflate(layoutInflater, contentView, contentView != null)
    }

    private var valueChangeCallback: ((NumberPicker, Int, Int) -> Unit)? = null

    init {
        binding.numberPicker.apply {
            // 关闭可编辑模式
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            setOnValueChangedListener { picker, oldVal, newVal ->
                valueChangeCallback?.invoke(picker, oldVal, newVal)
            }
        }

        setGravityBottom()
    }

    /**
     * 设置最大值
     */
    fun setMaxValue(maxValue: Int) {
        binding.numberPicker.maxValue = maxValue
    }

    /**
     * 设置最小值
     */
    fun setMinValue(minValue: Int) {
        binding.numberPicker.minValue = minValue
    }

    /**
     * 设置当前值
     */
    var value: Int
        get() = binding.numberPicker.value
        set(value) {
            binding.numberPicker.value = value
        }

    /**
     * 设置数值改变事件
     */
    fun doOnValueChanged(callback: ((view: NumberPicker, oldVal: Int, newVal: Int) -> Unit)?) {
        this.valueChangeCallback = callback
    }

    class Builder(private val context: Context) {
        private var maxValue = 0
        private var minValue = 0
        private var value = 0
        private var valueChangeCallback: ((NumberPicker, Int, Int) -> Unit)? = null
        private var onDismissListener: DialogInterface.OnDismissListener? = null

        fun setMaxValue(maxValue: Int) = apply {
            this.maxValue = maxValue
        }

        fun setMinValue(minValue: Int) = apply {
            this.minValue = minValue
        }

        fun setValue(value: Int) = apply {
            this.value = value
        }

        fun doOnValueChanged(callback: ((view: NumberPicker, oldVal: Int, newVal: Int) -> Unit)?) =
            apply {
                this.valueChangeCallback = callback
            }

        fun doOnDismiss(listener: DialogInterface.OnDismissListener?) = apply {
            onDismissListener = listener
        }

        fun create(): NumberPickerDialog = NumberPickerDialog(context).apply {
            setMaxValue(maxValue)
            setMinValue(minValue)
            value = this@Builder.value
            doOnValueChanged(this@Builder.valueChangeCallback)
            setOnDismissListener(onDismissListener)
        }

        fun show(): NumberPickerDialog = create().apply {
            show()
        }
    }
}