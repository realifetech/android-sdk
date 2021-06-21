package com.realifetech.core_sdk.data.shared.translation

interface HasTranslation<T : Translation?> {
    val translations: List<T>?
}