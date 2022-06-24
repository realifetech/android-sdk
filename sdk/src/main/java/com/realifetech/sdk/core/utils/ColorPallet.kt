package com.realifetech.sdk.core.utils

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.realifetech.realifetech_sdk.R
import javax.inject.Inject

// require context, should be tested using UI test

class ColorPallet (context: Context) {
    // Color Primary


    @ColorInt
    var colorPrimary: Int =
        ContextCompat.getColor(context, R.color.RLT_SDK_Color_Primary)

    // Color OnPrimary
    @ColorInt
    var colorOnPrimary: Int =
        ContextCompat.getColor(context, R.color.RLT_SDK_Color_OnPrimary)

    // Color Surface
    @ColorInt
    var colorSurface: Int =
        ContextCompat.getColor(context, R.color.RLT_SDK_Color_Surface)

    // Color OnSurface
    @ColorInt
    var colorOnSurface: Int =
        ContextCompat.getColor(context, R.color.RLT_SDK_Color_OnSurface)

    // Color OnSurface
    @ColorInt
    var colorNeutral: Int =
        ContextCompat.getColor(context, R.color.RLT_SDK_Color_Neutral)


}