package com.junpu.widget.dial

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat

/**
 *  拨号盘
 *
 * @author chengxiaobo
 * @time 2018/6/22 15:01
 */
class DialPlate : LinearLayout, View.OnClickListener, View.OnFocusChangeListener {
    companion object {
        val number = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        val numberId = listOf(
            R.id.tv0,
            R.id.tv1,
            R.id.tv2,
            R.id.tv3,
            R.id.tv4,
            R.id.tv5,
            R.id.tv6,
            R.id.tv7,
            R.id.tv8,
            R.id.tv9
        )
    }

    var dialClick: OnDialClickListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        if (context != null) {
            View.inflate(context, R.layout.view_dial_plate, this)
            for (i in numberId) {
                findViewById<TextView>(i).onFocusChangeListener = this
                findViewById<TextView>(i).setOnClickListener(this)
            }
            findViewById<FrameLayout>(R.id.flDelete).setOnClickListener(this)
            findViewById<FrameLayout>(R.id.flDelete).onFocusChangeListener = this

            findViewById<FrameLayout>(R.id.flDial).setOnClickListener(this)
            findViewById<FrameLayout>(R.id.flDial).onFocusChangeListener = this
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.flDelete -> {
                val tvPhoneNumber = findViewById<TextView>(R.id.tvPhoneNumber)
                if (tvPhoneNumber.text.toString().isEmpty()) {
                    return
                } else {
                    tvPhoneNumber.text =
                        tvPhoneNumber.text.toString().substring(0, tvPhoneNumber.text.length - 1)
                }
            }
            R.id.flDial -> {
                val tvPhoneNumber = findViewById<TextView>(R.id.tvPhoneNumber)
                dialClick?.click(tvPhoneNumber.text.toString())
            }
            else -> {
                v?.let {
                    val index = numberId.indexOf(v.id)
                    index.let {
                        val tvPhoneNumber = findViewById<TextView>(R.id.tvPhoneNumber)
                        if (tvPhoneNumber.text.toString().trim().length < 11) {
                            findViewById<TextView>(R.id.tvPhoneNumber).append(number[index])
                        }
                    }
                }
            }
        }
    }

    fun getDailNumTextView(): TextView {
        return (findViewById(R.id.tvPhoneNumber))
    }

    fun getTextView1(): TextView {
        return (findViewById(R.id.tv1))
    }

    fun getTextView2(): TextView {
        return (findViewById(R.id.tv2))
    }

    fun getTextView3(): TextView {
        return (findViewById(R.id.tv3))
    }

    fun getTextView4(): TextView {
        return (findViewById(R.id.tv4))
    }

    fun getTextView5(): TextView {
        return (findViewById(R.id.tv5))
    }

    fun getTextView6(): TextView {
        return (findViewById(R.id.tv6))
    }

    fun getTextView7(): TextView {
        return (findViewById(R.id.tv7))
    }

    fun getTextView8(): TextView {
        return (findViewById(R.id.tv8))
    }

    fun getTextView9(): TextView {
        return (findViewById(R.id.tv9))
    }

    fun getTextView0(): TextView {
        return (findViewById(R.id.tv0))
    }

    fun getFrameLayoutDail(): FrameLayout {
        return (findViewById(R.id.flDial))
    }

    fun getFrameLayoutDelete(): FrameLayout {
        return (findViewById(R.id.flDelete))
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {

            ViewCompat.setTranslationZ(v?.parent as View, 1.0f)
            ViewCompat.animate(v).scaleX(1.05f).scaleY(1.05f).translationZ(1.0f).start()
        } else {
            ViewCompat.setTranslationZ(v?.parent as View, 0.0f)
            ViewCompat.animate(v).scaleX(1f).scaleY(1f).translationZ(0.0f).start()
        }
        if (v.id == R.id.flDelete) {
            findViewById<ImageView>(R.id.ivDelete).isSelected = hasFocus
        }

        if (v.id == R.id.flDial) {
            findViewById<ImageView>(R.id.ivDial).isSelected = hasFocus
        }
    }

    interface OnDialClickListener {
        fun click(dialNumber: String)
    }
}