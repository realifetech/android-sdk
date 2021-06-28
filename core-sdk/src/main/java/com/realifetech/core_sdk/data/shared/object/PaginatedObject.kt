package com.realifetech.core_sdk.data.shared.`object`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaginatedObject<T : Parcelable?>(val items: List<T>?, val nextPage: Int?) : Parcelable