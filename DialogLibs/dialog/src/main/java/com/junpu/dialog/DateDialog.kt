package com.junpu.dialog

import android.content.Context
import android.content.DialogInterface
import android.widget.DatePicker
import com.junpu.dialog.databinding.DialogDateBinding
import java.util.*

/**
 * 用户选择生日dialog
 * @author junpu
 * @date 2019-12-23
 */
class DateDialog(context: Context) : BaseDialog(context) {

    private val binding by lazy {
        DialogDateBinding.inflate(layoutInflater, contentView, contentView != null)
    }

    private var startDateChangedCallback: ((DatePicker, Int, Int, Int) -> Unit)? = null
    private var endDateChangedCallback: ((DatePicker, Int, Int, Int) -> Unit)? = null
    private var submitCallback: ((DateDialog, Long, Long) -> Unit)? = null

    init {
        setCanceledOnTouchOutside(true)
        binding.run {
            datePickerStart.maxDate = System.currentTimeMillis()
            datePickerEnd.maxDate = System.currentTimeMillis()
            btnCancel.setOnClickListener { cancel() }
            btnSubmit.setOnClickListener {
                submitCallback?.invoke(this@DateDialog, startDate, endDate)
                cancel()
            }
        }

        setWidthMatchParent()
    }

    fun doOnStartDateChanged(callback: ((view: DatePicker, year: Int, month: Int, day: Int) -> Unit)?) {
        this.startDateChangedCallback = callback
    }

    fun doOnEndDateChanged(callback: ((view: DatePicker, year: Int, month: Int, day: Int) -> Unit)?) {
        this.endDateChangedCallback = callback
    }

    fun doOnSubmit(callback: ((dialog: DateDialog, start: Long, end: Long) -> Unit)?) {
        submitCallback = callback
    }

    /**
     * 设置开始时间范围
     */
    fun setStartDateRange(minDate: Long, maxDate: Long) {
        if (minDate > 0) binding.datePickerStart.minDate = minDate
        if (maxDate > 0) binding.datePickerStart.maxDate = maxDate
    }

    /**
     * 设置结束时间范围
     */
    fun setEndDateRange(minDate: Long, maxDate: Long) {
        if (minDate > 0) binding.datePickerEnd.minDate = minDate
        if (maxDate > 0) binding.datePickerEnd.maxDate = maxDate
    }

    /**
     * 转化时间戳 -> 年月日
     */
    private fun transformDate(timeMillis: Long = 0): IntArray { // 用户的生日
        val calendar = Calendar.getInstance().apply {
            timeInMillis = if (timeMillis <= 0) System.currentTimeMillis() else timeMillis
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return intArrayOf(year, month, day)
    }

    /**
     * 转化年月日 -> 时间戳
     */
    private fun transformDate(year: Int, month: Int, dayOfMonth: Int) =
        Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }.timeInMillis

    /**
     * 初始化年月日
     */
    fun initDate(startTime: Long, endTime: Long) {
        val start = transformDate(startTime)
        val end = transformDate(endTime)
        initStartDate(start[0], start[1], start[2])
        initEndDate(end[0], end[1], end[2])
    }

    /**
     * 初始化StartTime
     */
    fun initStartDate(year: Int, month: Int, day: Int) {
        binding.datePickerStart.init(
            year,
            month,
            day
        ) { datePicker, years, monthOfYear, dayOfMonth ->
            startDateChangedCallback?.invoke(datePicker, years, monthOfYear, dayOfMonth)
        }
    }

    /**
     * 初始化EndTime
     */
    fun initEndDate(year: Int, month: Int, day: Int) {
        binding.datePickerEnd.init(year, month, day) { datePicker, years, monthOfYear, dayOfMonth ->
            endDateChangedCallback?.invoke(datePicker, years, monthOfYear, dayOfMonth)
        }
    }

    /**
     * 获取当前显示时间
     */
    val startDate: Long
        get() {
            val year = binding.datePickerStart.year
            val month = binding.datePickerStart.month
            val day = binding.datePickerStart.dayOfMonth
            return transformDate(year, month, day)
        }

    /**
     * 获取当前显示时间
     */
    val endDate: Long
        get() {
            val year = binding.datePickerEnd.year
            val month = binding.datePickerEnd.month
            val day = binding.datePickerEnd.dayOfMonth
            return transformDate(year, month, day)
        }

    class Builder(private val context: Context) {
        private var startTime: Long = 0
        private var endTime: Long = 0
        private var startDateChangedCallback: ((DatePicker, Int, Int, Int) -> Unit)? = null
        private var endDateChangedCallback: ((DatePicker, Int, Int, Int) -> Unit)? = null
        private var submitCallback: ((DateDialog, Long, Long) -> Unit)? = null
        private var onDismissListener: DialogInterface.OnDismissListener? = null
        private var startMinDate: Long = 0
        private var startMaxDate: Long = 0
        private var endMinDate: Long = 0
        private var endMaxDate: Long = 0

        fun setStartDateRange(minDate: Long, maxDate: Long) = apply {
            startMinDate = minDate
            startMaxDate = maxDate
        }

        fun setEndDateRange(minDate: Long, maxDate: Long) = apply {
            endMinDate = minDate
            endMaxDate = maxDate
        }

        fun initDate(startTime: Long, endTime: Long) = apply {
            this.startTime = startTime
            this.endTime = endTime
        }

        fun doOnStartDateChanged(callback: ((view: DatePicker, year: Int, month: Int, day: Int) -> Unit)?) =
            apply {
                this.startDateChangedCallback = callback
            }

        fun doOnEndDateChanged(callback: ((view: DatePicker, year: Int, month: Int, day: Int) -> Unit)?) =
            apply {
                this.endDateChangedCallback = callback
            }

        fun doOnSubmit(callback: ((dialog: DateDialog, start: Long, end: Long) -> Unit)?) =
            apply {
                this.submitCallback = callback
            }

        fun doOnDismiss(listener: DialogInterface.OnDismissListener?) = apply {
            onDismissListener = listener
        }

        fun create() = DateDialog(context).apply {
            setStartDateRange(startMinDate, startMaxDate)
            setEndDateRange(endMinDate, endMaxDate)
            initDate(startTime, endTime)
            doOnStartDateChanged(this@Builder.startDateChangedCallback)
            doOnEndDateChanged(this@Builder.endDateChangedCallback)
            doOnSubmit(this@Builder.submitCallback)
            setOnDismissListener(onDismissListener)
        }

        fun show() = create().apply {
            show()
        }
    }
}