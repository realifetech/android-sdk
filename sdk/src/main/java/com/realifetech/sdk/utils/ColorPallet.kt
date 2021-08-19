package com.realifetech.sdk.utils

import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.realifetech_sdk.R

// require context, should be tested using UI test
internal object ColorPallet {
    // Color Primary
    @ColorInt
    var colorPrimary: Int =
        ContextCompat.getColor(CoreConfiguration.context, R.color.RLT_SDK_Color_Primary)

    // Color OnPrimary
    @ColorInt
    var colorOnPrimary: Int =
        ContextCompat.getColor(CoreConfiguration.context, R.color.RLT_SDK_Color_OnPrimary)

    // Color Surface
    @ColorInt
    var colorSurface: Int =
        ContextCompat.getColor(CoreConfiguration.context, R.color.RLT_SDK_Color_Surface)

    // Color OnSurface
    @ColorInt
    var colorOnSurface: Int =
        ContextCompat.getColor(CoreConfiguration.context, R.color.RLT_SDK_Color_OnSurface)

    // Color OnSurface
    @ColorInt
    var colorNeutral: Int =
        ContextCompat.getColor(CoreConfiguration.context, R.color.RLT_SDK_Color_Neutral)


}