package com.junpu.widget.phonedial

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

/**
 * 拨号盘
 * @author Junpu
 * @time 2018/6/27 11:30
 */
class PhoneDialView : FrameLayout, OnClickListener {

    private var textDialNumber: EditText? = null
    private var dialCall: TextView? = null
    private var listener: OnDialNumberListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_phone_dial, this)
        findViewById<TextView>(R.id.number0).setOnClickListener(this)
        findViewById<TextView>(R.id.number1).setOnClickListener(this)
        findViewById<TextView>(R.id.number2).setOnClickListener(this)
        findViewById<TextView>(R.id.number3).setOnClickListener(this)
        findViewById<TextView>(R.id.number4).setOnClickListener(this)
        findViewById<TextView>(R.id.number5).setOnClickListener(this)
        findViewById<TextView>(R.id.number6).setOnClickListener(this)
        findViewById<TextView>(R.id.number7).setOnClickListener(this)
        findViewById<TextView>(R.id.number8).setOnClickListener(this)
        findViewById<TextView>(R.id.number9).setOnClickListener(this)
        findViewById<ImageView>(R.id.dialDel).setOnClickListener(this)
        findViewById<ImageView>(R.id.dialDel).setOnLongClickListener {
            setDialNumber(null)
            true
        }
        dialCall = findViewById(R.id.dialCall)
        dialCall?.setOnClickListener(this)
        initDialCall()

        textDialNumber = findViewById(R.id.textDialNumber)
        textDialNumber?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                textDialNumber?.textSize = if (s.isNullOrEmpty()) 20.9f else 26.9f
            }
        })
    }

    private fun initDialCall() {
        val screenWidth = context.resources.displayMetrics.widthPixels
        val textWidth = context.sp(26.9f)
        dialCall?.layoutParams?.width = screenWidth * 2 / 3 + textWidth
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.number0, R.id.number1, R.id.number2, R.id.number3, R.id.number4, R.id.number5,
            R.id.number6, R.id.number7, R.id.number8, R.id.number9 -> appendNumber(v)
            R.id.dialCall -> callNumber()
            R.id.dialDel -> delNumber()
        }
    }

    private fun appendNumber(v: View?) {
        if (v is TextView) textDialNumber?.append(v.text)
    }

    private fun delNumber() {
        val result = textDialNumber?.text
        if (!TextUtils.isEmpty(result)) textDialNumber?.setText(
            result?.subSequence(
                0,
                result.length - 1
            )
        )
    }

    private fun callNumber() {
        val text = textDialNumber?.text.toString()
        listener?.onDialNumber(text)
    }

    /**
     * 设置号码
     */
    fun setDialNumber(number: String?) {
        textDialNumber?.setText(number)
    }

    fun setOnDialNumberListener(listener: OnDialNumberListener) {
        this.listener = listener
    }

    interface OnDialNumberListener {
        fun onDialNumber(number: String?)
    }

    /**
     * SP转PX
     */
    private fun Context.sp(sp: Float) = (resources.displayMetrics.scaledDensity * sp).toInt()
}