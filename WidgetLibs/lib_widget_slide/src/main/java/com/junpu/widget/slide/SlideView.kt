package com.junpu.widget.slide

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlin.math.abs

/**
 * 侧滑View
 *
 * @author chengxiaobo
 * @time 2018/6/15 14:58
 */
class SlideView : FrameLayout {
    companion object {
        const val MIN_MOVE = 2.0f //dp
    }

    private var handleWidth = 0.0f
    private var handleHeight = 0.0f
    private var downTranslationX = 0.0f
    private var downX = 0f
    private var downY = 0f
    private var animator: ObjectAnimator? = null
    private var isMove = false //是否移动，没有移动,模拟点击
    private var minMovePx = 0 //最小移动距离
    private var clickListener: ClickListener? = null
    private lateinit var container: FrameLayout
    private var isStretch = false //是否是展开状态
    private var hastrend = false//是否确定趋势了
    private var trendPx = 0.0f //确定趋势的px值

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        minMovePx = context.dip(MIN_MOVE)
        trendPx = context.dip(MIN_MOVE) * 1.0f
        View.inflate(context, R.layout.view_slide, this)
        container = findViewById(R.id.container)
        findViewById<View>(R.id.btnDelete).setOnClickListener {
            if (isStretch) {
                clickListener?.clickDelete()
                shrink()
            }

        }
        findViewById<View>(R.id.btnNickName).setOnClickListener {
            if (isStretch) {
                clickListener?.clickAddNick()
                shrink()
            }
        }
        this.post {
            findViewById<LinearLayout>(R.id.llHandle).run {
                handleWidth = width.toFloat()
                handleHeight = height.toFloat()
            }
        }
    }

    fun setListener(listener: ClickListener?) {
        clickListener = listener
    }

    fun addContainerView(view: View) {
        container.removeAllViews()
        container.addView(view)
    }

    /**
     * 收缩
     */
    fun shrink(noAnimation: Boolean = false) {
        isStretch = false
        if (noAnimation) startAnimation(0.0f, 0L) else startAnimation(0.0f)

    }

    /**
     * 伸开
     */
    private fun stretch() {
        isStretch = true
        startAnimation(-handleWidth)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        var intercept = false
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downTranslationX = container.translationX
                downX = event.rawX
                downY = event.rawY
                isMove = false
                intercept = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(event.rawY - downY) > minMovePx && Math.abs(event.rawX - downX) > minMovePx) {
                    isMove = true
                    intercept = true
                }
            }
            else -> {
                intercept = false
            }
        }
        return intercept
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("onTouch", "=============onTouchEvent======Move=======")
                if (abs(event.rawY - downY) > minMovePx && Math.abs(event.rawX - downX) > minMovePx) {
                    isMove = true
                }
                if (abs(event.rawY - downY) > trendPx || Math.abs(event.rawX - downX) > trendPx) {
                    hastrend = true

                    if (abs(event.rawY - downY) < Math.abs(event.rawX - downX)) {
                        requestDisallowInterceptTouchEvent(true)
                    } else {
                        return false
                    }
                }
                var x = downTranslationX + event.rawX - downX
                if (x > 0) x = 0.0f
                if (x < -handleWidth) x = -handleWidth
                container.translationX = x
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                val t = if (container.translationX < -handleWidth / 2) {
                    isStretch = true
                    -handleWidth
                } else 0.0f
                startAnimation(t)
                if (!isMove) {
                    performClick()
                }
            }
        }
        return true
    }

    private fun startAnimation(t: Float, duration: Long = 100L) {
        animator = ObjectAnimator.ofFloat(container, "translationX", container.translationX, t)
        animator?.duration = duration
        animator?.interpolator = AccelerateInterpolator()
        animator?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }

    interface ClickListener {
        fun clickDelete()
        fun clickAddNick()
    }

    private fun Context.dip(dp: Float) = (context.resources.displayMetrics.density * dp).toInt()
}
