package com.realifetech.sdk.core.data.model.fulfilmentPoint

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionNotes(
    val virtualQueuePreOrder: String?,
    val virtualQueueCheckin: String?
) : Parcelable