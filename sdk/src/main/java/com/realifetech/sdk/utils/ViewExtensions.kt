package com.realifetech.sdk.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import com.realifetech.sdk.utils.ColorPallet.colorNeutral
import com.realifetech.sdk.utils.ColorPallet.colorOnSurface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


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

@ExperimentalCoroutinesApi
@CheckResult
fun View.clicks(): Flow<View> {
    return callbackFlow {
        setOnClickListener { safeOffer(it) }
        awaitClose { setOnClickListener(null) }
    }
}

@ExperimentalCoroutinesApi
@CheckResult
fun MenuItem.clicks(): Flow<MenuItem> {
    return callbackFlow {
        setOnMenuItemClickListener { safeOffer(it) }
        awaitClose { setOnMenuItemClickListener(null) }
    }
}

internal fun <T> SendChannel<T>.safeOffer(element: T): Boolean {
    return runCatching { offer(element) }.getOrDefault(false)
}