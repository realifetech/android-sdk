package com.realifetech.sdk.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import com.realifetech.sdk.utils.ColorPallet.colorNeutral
import com.realifetech.sdk.utils.ColorPallet.colorOnSurface


fun Drawable.tint(@ColorInt colorId: Int) {
    this.let {
        DrawableCompat.wrap(it)
        DrawableCompat.setTint(it, colorId)
    }
}

fun ImageView.setTaggableOnSurfaceTint() {
    val states: Array<IntArray> =
        arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf())
    val colors: IntArray = intArrayOf(colorOnSurface, colorNeutral)
    imageTintList = ColorStateList(states, colors)
}
