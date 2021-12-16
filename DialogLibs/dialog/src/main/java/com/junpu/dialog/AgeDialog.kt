package com.junpu.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.DatePicker
import com.junpu.dialog.databinding.DialogAgeBinding

/**
 * 用户选择生日dialog
 * @author junpu
 * @date 2019-12-23
 */
class AgeDialog(context: Context) : BaseDialog(context) {

    private val binding by lazy {
        DialogAgeBinding.inflate(layoutInflater, contentView, contentView != null)
    }

    private var dateChangedCallback: ((DatePicker, Int, Int, Int) -> Unit)? = null

    init {
        setCanceledOnTouchOutside(true)
        binding.datePicker.maxDate = System.currentTimeMillis()
    }

    /**
     * 初始化年月日
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
     */
    fun initDate(year: Int, month: Int, day: Int) {
        binding.datePicker.init(year, month - 1, day) { v, y, m, d ->
            dateChangedCallback?.invoke(v, y, m, d)
        }
    }

    fun doOnDateChanged(callback: ((DatePicker, year: Int, month: Int, day: Int) -> Unit)?) {
        this.dateChangedCallback = callback
    }

    /**
     * 获取当前显示时间
     */
    val date: IntArray
        get() {
            val year = binding.datePicker.year
            val month = binding.datePicker.month
            val day = binding.datePicker.dayOfMonth
            return intArrayOf(year, month, day)
        }

    val year: Int get() = binding.datePicker.year

    val month: Int get() = binding.datePicker.month

    val day: Int get() = binding.datePicker.dayOfMonth

    private fun parseIntSafely(number: String?): Int {
        if (number.isNullOrBlank()) return 0
        try {
            return number.toInt()
        } catch (e: NumberFormatException) {
            Log.e("System.err", e.stackTraceToString())
        }
        return 0
    }

    class Builder(private val context: Context) {
        private var initYear: Int = -1
        private var initMonth: Int = -1
        private var initDay: Int = -1
        private var birthday: String? = null
        private var dateChangedCallback: ((DatePicker, Int, Int, Int) -> Unit)? = null
        private var onDismissListener: DialogInterface.OnDismissListener? = null

        fun initDate(birthday: String?) = apply {
            this.birthday = birthday
        }

        fun initDate(year: Int, month: Int, day: Int) {
            this.initYear = year
            this.initMonth = month
            this.initDay = day
        }

        fun doOnDateChanged(callback: ((view: DatePicker, year: Int, month: Int, day: Int) -> Unit)?) =
            apply {
                this.dateChangedCallback = callback
            }

        fun doOnDismiss(listener: DialogInterface.OnDismissListener?) = apply {
            this.onDismissListener = listener
        }

        fun create() = AgeDialog(context).apply {
            if (initYear != -1 && initMonth != -1 && initDay != -1)
                initDate(initYear, initMonth, initDay)
            else if (!birthday.isNullOrBlank())
                initDate(birthday)
            doOnDateChanged(this@Builder.dateChangedCallback)
            setOnDismissListener(this@Builder.onDismissListener)
        }

        fun show() = create().apply {
            show()
        }
    }
}