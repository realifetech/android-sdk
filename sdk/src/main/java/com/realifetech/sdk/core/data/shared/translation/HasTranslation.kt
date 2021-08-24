package com.realifetech.sdk.core.data.shared.translation

interface HasTranslation<T : Translation?> {
    val translations: List<T>?
}