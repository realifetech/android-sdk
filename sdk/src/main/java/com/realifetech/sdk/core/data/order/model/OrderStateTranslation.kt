package com.realifetech.sdk.core.data.order.model

import android.os.Parcelable
import com.realifetech.sdk.core.data.shared.translation.Translation
import kotlinx.android.parcel.Parcelize

@Parcelize
class OrderStateTranslation(
    val id: String?,
    override val language: String?,
    val title: String?,
    val description: String?
) : Parcelable, Translation