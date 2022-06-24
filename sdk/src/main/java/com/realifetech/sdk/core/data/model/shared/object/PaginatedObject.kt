package com.realifetech.sdk.core.data.model.shared.`object`

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaginatedObject<T : Parcelable?>(val items: List<T>?, val nextPage: Int?) : Parcelable