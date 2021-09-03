package com.realifetech.sdk.core.data.model.fulfilmentPoint

import android.os.Parcelable
import com.realifetech.sdk.core.data.model.shared.translation.Translation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FulfilmentPointTranslation(
    override val language: String?,
    val title: String?,
    val description: String?,
    val collectionNote: String?,
    val collectionNotes: CollectionNotes?
) : Parcelable, Translation