package com.junpu.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.DatePicker

/**
 * 用户选择生日dialog
 * @author junpu
 * @date 2019-12-23
 */
class AgeDialog : BaseDialog {

    private var datePicker: DatePicker? = null
    private var listener: ((view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) -> Unit)? =
        null

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    fun setOnDateChangedListener(listener: ((view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) -> Unit)?) {
        this.listener = listener
    }

    override fun layout(): Int = R.layout.dialog_age

    override fun initWindow(window: Window, params: WindowManager.LayoutParams) {
        window.setGravity(Gravity.BOTTOM)
        window.setWindowAnimations(R.style.dialog_anim_bottom)
    }

    override fun initView() {
        setCanceledOnTouchOutside(true)
        datePicker = findViewById(R.id.datePicker)
        datePicker?.maxDate = System.currentTimeMillis()
    }

    /**
     * 初始化年月日
     *
     * @param birthday 格式：1900-01-01
     */
    fun initDate(birthday: String?) { // 用户的生日
        var year = 1990
        var month = 1
        var day = 1
        if (!birthday.isNullOrBlank()) {
            val birth = birthday.split("-").toTypedArray()
            year = parseIntSafely(birth[0])
            month = parseIntSafely(birth[1])
            day = parseIntSafely(birth[2])
        }
        initDate(year, month, day)
    }

    /**
     * 初始化年月日
     *
     * @param year
     * @param month
     * @param day
     */
    fun initDate(year: Int, month: Int, day: Int) {
        datePicker?.init(year, month - 1, day) { datePicker, years, monthOfYear, dayOfMonth ->
            listener?.invoke(datePicker, years, monthOfYear, dayOfMonth)
        }
    }

    /**
     * 获取当前显示时间
     */
    val date: IntArray
        get() {
            val year = datePicker?.year ?: -1
            val month = datePicker?.month ?: -1
            val day = datePicker?.dayOfMonth ?: -1
            return intArrayOf(year, month, day)
        }

    val year: Int
        get() = datePicker?.year ?: -1

    val month: Int
        get() = datePicker?.month ?: -1

    val day: Int
        get() = datePicker?.dayOfMonth ?: -1

    private fun parseIntSafely(number: String?): Int {
        if (number.isNullOrBlank()) return 0
        try {
            return number.toInt()
        } catch (exception: NumberFormatException) {
            Log.e("System.err", Log.getStackTraceString(exception))
        }
        return 0
    }

    class Builder(private val mContext: Context) {
        private var birthday: String? = null
        private var listener: ((DatePicker, Int, Int, Int) -> Unit)? = null
        private var onDismissListener: DialogInterface.OnDismissListener? = null

        fun initDate(birthday: String?): Builder = apply {
            this.birthday = birthday
        }

        fun setOnDateChangedListener(listener: ((view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) -> Unit)?): Builder =
            apply {
                this.listener = listener
            }

        fun setOnDismissListener(listener: DialogInterface.OnDismissListener?): Builder = apply {
            this.onDismissListener = listener
        }

        fun create() = AgeDialog(mContext).apply {
            initDate(birthday)
            setOnDateChangedListener(this@Builder.listener)
            setOnDismissListener(this@Builder.onDismissListener)
        }

        fun show() = create().apply {
            show()
        }
    }
}