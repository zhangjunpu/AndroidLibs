package com.junpu.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.NumberPicker

/**
 * 列表对话框
 */
class NumberPickerDialog : BaseDialog {

    private var numberPicker: NumberPicker? = null
    private var onValueChangeListener: ((picker: NumberPicker, oldVal: Int, newVal: Int) -> Unit)? =
        null

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    override fun layout(): Int = R.layout.dialog_numberpicker

    override fun initWindow(window: Window, params: WindowManager.LayoutParams) {
        window.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
    }

    override fun initView() {
        numberPicker = findViewById<NumberPicker>(R.id.numberPicker)?.apply {
            // 关闭可编辑模式
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            setOnValueChangedListener { picker, oldVal, newVal ->
                onValueChangeListener?.invoke(picker, oldVal, newVal)
            }
        }
    }

    /**
     * 设置最大值
     */
    fun setMaxValue(maxValue: Int) {
        numberPicker?.maxValue = maxValue
    }

    /**
     * 设置最小值
     */
    fun setMinValue(minValue: Int) {
        numberPicker?.minValue = minValue
    }

    /**
     * 设置当前值
     */
    var value: Int
        get() = numberPicker?.value ?: -1
        set(value) {
            numberPicker?.value = value
        }

    /**
     * 设置数值改变事件
     */
    fun setOnValueChangedListener(onValueChangedListener: ((picker: NumberPicker, oldVal: Int, newVal: Int) -> Unit)?) {
        this.onValueChangeListener = onValueChangedListener
    }

    class Builder(private val context: Context) {
        private var maxValue = 0
        private var minValue = 0
        private var value = 0
        private var onValueChangeListener: ((picker: NumberPicker, oldVal: Int, newVal: Int) -> Unit)? =
            null
        private var onDismissListener: ((DialogInterface) -> Unit)? = null

        fun setMaxValue(maxValue: Int): Builder = apply {
            this.maxValue = maxValue
        }

        fun setMinValue(minValue: Int): Builder = apply {
            this.minValue = minValue
        }

        fun setValue(value: Int): Builder = apply {
            this.value = value
        }

        fun setOnValueChangedListener(listener: ((picker: NumberPicker, oldVal: Int, newVal: Int) -> Unit)?): Builder =
            apply {
                this.onValueChangeListener = listener
            }

        fun setOnDismissListener(listener: ((DialogInterface) -> Unit)?): Builder = apply {
            onDismissListener = listener
        }

        fun create(): NumberPickerDialog = NumberPickerDialog(context).apply {
            setMaxValue(maxValue)
            setMinValue(minValue)
            value = this@Builder.value
            setOnValueChangedListener(this@Builder.onValueChangeListener)
            setOnDismissListener(this@Builder.onDismissListener)
        }

        fun show(): NumberPickerDialog = create().apply {
            show()
        }
    }
}