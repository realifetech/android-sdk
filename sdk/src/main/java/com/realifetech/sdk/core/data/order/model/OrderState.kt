package com.realifetech.sdk.core.data.order.model

import android.os.Parcelable
import com.realifetech.sdk.core.data.shared.translation.HasTranslation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderState(
    val status: String?,
    val workingState: String?,
    override val translations: List<OrderStateTranslation?>?
) : Parcelable, HasTranslation<OrderStateTranslation?>
