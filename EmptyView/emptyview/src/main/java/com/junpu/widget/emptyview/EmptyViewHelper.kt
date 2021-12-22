@file:JvmName("EmptyViewHelper")

package com.junpu.widget.emptyview

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 *
 * @author junpu
 * @date 2019-12-26
 */

internal fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

internal fun View.visible() {
    visibility = View.VISIBLE
}

internal fun View.gone() {
    visibility = View.GONE
}

internal inline val View.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)

internal var View.backgroundResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
    set(v) = setBackgroundResource(v)

internal var ImageView.imageResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
    set(v) = setImageResource(v)

internal var TextView.textColor: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
    set(v) = setTextColor(v)

internal var TextView.textColorResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
    set(colorId) = setTextColor(context.resources.getColor(colorId))

internal var TextView.textResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
    set(stringId) {
        text = context.resources.getString(stringId)
    }


internal const val NO_GETTER: String = "Property does not have a getter"
internal fun noGetter(): Nothing = throw RuntimeException("Property does not have a getter")