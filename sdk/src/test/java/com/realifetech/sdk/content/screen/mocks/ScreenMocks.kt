package com.realifetech.sdk.content.screen.mocks

import com.realifetech.GetScreenByIdQuery
import com.realifetech.GetScreenByScreenTypeQuery
import com.realifetech.sdk.content.screen.data.model.mapToTranslation
import com.realifetech.type.Language
import com.realifetech.type.Language.EN
import com.realifetech.type.Language.SV
import com.realifetech.type.ScreenType

object ScreenMocks {

    val screenType = ScreenType.DISCOVER
    val engTranslation = getTranslationById("Discover", EN)
    val svTranslation = getTranslationById("who knows", SV)
    val engTranslationByType = getTranslationByType("Discover", EN)
    val svTranslationByType = getTranslationByType("who knows", SV)
    val translationsById: List<GetScreenByIdQuery.Translation>? = listOf(engTranslation, svTranslation)
    val translationsByType: List<GetScreenByScreenTypeQuery.Translation>? = listOf(engTranslationByType, svTranslationByType)
    val screenId = "1234"
    val resultTranslationById = translationsById?.map { it.mapToTranslation() }
    val resultTranslationByType = translationsByType?.map { it.mapToTranslation() }
    private fun getTranslationById(title: String, lang: Language) =
        GetScreenByIdQuery.Translation("", lang, title)
    private fun getTranslationByType(title: String, lang: Language) =
        GetScreenByScreenTypeQuery.Translation("", lang, title)
}