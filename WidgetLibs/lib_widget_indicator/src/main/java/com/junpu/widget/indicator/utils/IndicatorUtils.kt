package com.junpu.widget.indicator.utils

import android.content.Context
import android.util.TypedValue

/**
 *
 * @author junpu
 * @time 2017/8/2 16:29
 */

object IndicatorUtils {

    /**
     * 返回dp值
     */
    @JvmStatic fun dp2px(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }

    /**
     * 返回dp值
     */
    @JvmStatic fun dp2px(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

}
