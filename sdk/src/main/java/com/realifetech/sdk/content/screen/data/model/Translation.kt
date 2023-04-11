package com.realifetech.sdk.content.screen.data.model

import com.realifetech.GetScreenByIdQuery
import com.realifetech.GetScreenByScreenTypeQuery
import com.realifetech.fragment.NotificationConsentFragment

data class Translation(val language: String, val title: String)

fun GetScreenByIdQuery.Translation.mapToTranslation(): Translation =
    Translation(this.language.name, this.title)

fun GetScreenByScreenTypeQuery.Translation.mapToTranslation(): Translation =
    Translation(this.language.name, this.title)

fun NotificationConsentFragment.Translation.mapToTranslation(): Translation =
    Translation(this.language, this.name)