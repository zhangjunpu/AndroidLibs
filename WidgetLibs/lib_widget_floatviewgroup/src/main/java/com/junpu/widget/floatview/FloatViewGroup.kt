package com.junpu.widget.floatview

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import kotlin.math.abs

/**
 * 浮动ViewGroup 可以拖拽，左右停靠
 *
 * @author chengxiaobo
 * @time 2018/6/15 14:58
 */
class FloatViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val MIN_MOVE = 2.0f //dp
    }

    private var parentWidth = 0.0f
    private var parentHeight = 0.0f

    private var downTranslationX = 0.0f
    private var downTranslationY = 0.0f
    private var downX = 0f
    private var downY = 0f

    private var isMove = false //是否移动，没有移动,模拟点击
    private var minMovePx = 0 //最小移动距离

    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "translationX", translationX).apply {
            duration = 100L
            interpolator = AccelerateInterpolator()
        }
    }

    init {
        post {
            val marginStart = (layoutParams as? MarginLayoutParams)?.marginStart ?: 0
            val marginEnd = (layoutParams as? MarginLayoutParams)?.marginEnd ?: 0
            val marginTop = (layoutParams as? MarginLayoutParams)?.topMargin ?: 0
            val marginBottom = (layoutParams as? MarginLayoutParams)?.bottomMargin ?: 0
            parentWidth = (parent as ViewGroup).width.toFloat() - marginStart - marginEnd
            parentHeight = (parent as ViewGroup).height.toFloat() - marginTop - marginBottom
        }

        minMovePx = context.dip(MIN_MOVE)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        var intercept = false
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downTranslationX = translationX
                downTranslationY = translationY
                downX = event.rawX
                downY = event.rawY
                isMove = false
                intercept = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (abs(event.rawY - downY) > minMovePx && abs(event.rawX - downX) > minMovePx) {
                    isMove = true
                    intercept = true
                }
            }
            else -> intercept = false
        }
        return intercept
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downTranslationX = translationX
                downTranslationY = translationY
                downX = event.rawX
                downY = event.rawY
                isMove = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (abs(event.rawY - downY) > minMovePx && abs(event.rawX - downX) > minMovePx) {
                    isMove = true
                }
                // 按钮初始位置在 parent 的右下角，所以往左上移动都是负向的，所以最大值为0
                // 如果 view 不在 parent 的右下角，需要调整这个最大值、最小值
                translationX = (downTranslationX + event.rawX - downX)
                    .coerceAtLeast(-(parentWidth - width))
                    .coerceAtMost(0f)
                translationY = (downTranslationY + event.rawY - downY)
                    .coerceAtLeast(-(parentHeight - height))
                    .coerceAtMost(0f)
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                val t =
                    if ((parentWidth - width) / 2 + translationX > 0) 0.0f else -(parentWidth - width)
                startAnimation(t)
                if (!isMove) performClick()
            }
        }
        return true
    }

    private fun startAnimation(t: Float) {
        animator.setFloatValues(t)
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    /**
     * 返回dp值
     */
    private fun Context.dip(dp: Float) = (resources.displayMetrics.density * dp).toInt()

}
