package com.realifetech.core_sdk.data.fulfilmentPoint

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionNotes(
    val virtualQueuePreOrder: String?,
    val virtualQueueCheckin: String?
) : Parcelable