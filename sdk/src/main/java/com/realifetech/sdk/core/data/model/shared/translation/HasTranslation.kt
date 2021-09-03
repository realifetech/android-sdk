package com.realifetech.sdk.core.data.model.shared.translation

interface HasTranslation<T : Translation?> {
    val translations: List<T>?
}