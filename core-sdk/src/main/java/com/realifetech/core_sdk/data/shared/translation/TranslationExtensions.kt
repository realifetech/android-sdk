package com.realifetech.core_sdk.data.shared.translation

import java.util.*

const val EMPTY = ""

fun <T : Translation> HasTranslation<T>.getTranslationForUserLanguage(): T? =
    translations?.let {

        for (translation in it) {

            if (translation.language == Locale.getDefault().language)
                return translation
        }

        return it.firstOrNull()
    }
