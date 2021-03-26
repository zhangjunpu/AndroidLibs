package com.junpu.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.view.Window
import android.view.WindowManager
import android.widget.DatePicker
import kotlinx.android.synthetic.main.dialog_date.*
import java.util.*

/**
 * 用户选择生日dialog
 * @author junpu
 * @date 2019-12-23
 */
class DateDialog : BaseDialog {

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

    override fun layout(): Int = R.layout.dialog_date

    override fun initWindow(window: Window, params: WindowManager.LayoutParams) {
        super.initWindow(window, params)
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun initView() {
        setCanceledOnTouchOutside(true)
        datePickerStart?.maxDate = System.currentTimeMillis()
        datePickerEnd?.maxDate = System.currentTimeMillis()
        btnCancel?.setOnClickListener { cancel() }
    }

    /**
     * 设置开始时间范围
     */
    fun setStartDateRange(minDate: Long, maxDate: Long) {
        if (minDate > 0) datePickerStart?.minDate = minDate
        if (maxDate > 0) datePickerStart?.maxDate = maxDate
    }

    /**
     * 设置结束时间范围
     */
    fun setEndDateRange(minDate: Long, maxDate: Long) {
        if (minDate > 0) datePickerEnd?.minDate = minDate
        if (maxDate > 0) datePickerEnd?.maxDate = maxDate
    }

    fun setOnSubmitListener(listener: ((dialog: DateDialog, start: Long, end: Long) -> Unit)?) {
        btnSubmit?.setOnClickListener {
            listener?.invoke(this, startDate, endDate)
        }
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
        datePickerStart?.init(year, month, day) { datePicker, years, monthOfYear, dayOfMonth ->
            listener?.invoke(datePicker, years, monthOfYear, dayOfMonth)
        }
    }

    /**
     * 初始化EndTime
     */
    fun initEndDate(year: Int, month: Int, day: Int) {
        datePickerEnd?.init(year, month, day) { datePicker, years, monthOfYear, dayOfMonth ->
            listener?.invoke(datePicker, years, monthOfYear, dayOfMonth)
        }
    }

    /**
     * 获取当前显示时间
     */
    val startDate: Long
        get() {
            val year = datePickerStart?.year ?: -1
            val month = datePickerStart?.month ?: -1
            val day = datePickerStart?.dayOfMonth ?: -1
            return transformDate(year, month, day)
        }

    /**
     * 获取当前显示时间
     */
    val endDate: Long
        get() {
            val year = datePickerEnd?.year ?: -1
            val month = datePickerEnd?.month ?: -1
            val day = datePickerEnd?.dayOfMonth ?: -1
            return transformDate(year, month, day)
        }

    class Builder(private val context: Context) {
        private var startTime: Long = 0
        private var endTime: Long = 0
        private var onDateChangedListener: ((DatePicker, Int, Int, Int) -> Unit)? = null
        private var onSubmitListener: ((DateDialog, Long, Long) -> Unit)? = null
        private var onDismissListener: ((DialogInterface) -> Unit)? = null
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

        fun initDate(startTime: Long, endTime: Long): Builder = apply {
            this.startTime = startTime
            this.endTime = endTime
        }

        fun setOnDateChangedListener(listener: ((view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) -> Unit)?): Builder =
            apply {
                this.onDateChangedListener = listener
            }

        fun setOnSubmitListener(listener: ((dialog: DateDialog, start: Long, end: Long) -> Unit)?) =
            apply {
                this.onSubmitListener = listener
            }

        fun setOnDismissListener(listener: ((DialogInterface) -> Unit)?) = apply {
            onDismissListener = listener
        }

        fun create() = DateDialog(context).apply {
            setStartDateRange(startMinDate, startMaxDate)
            setEndDateRange(endMinDate, endMaxDate)
            initDate(startTime, endTime)
            setOnDateChangedListener(this@Builder.onDateChangedListener)
            setOnSubmitListener(onSubmitListener)
            setOnDismissListener(onDismissListener)
        }

        fun show() = create().apply {
            show()
        }
    }
}