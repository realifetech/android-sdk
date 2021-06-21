package com.realifetech.core_sdk.data.order.model

import android.os.Parcelable
import com.realifetech.core_sdk.data.shared.translation.HasTranslation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderState(
    val status: String?,
    val workingState: String?,
    override val translations: List<OrderStateTranslation?>?
) : Parcelable, HasTranslation<OrderStateTranslation?>
