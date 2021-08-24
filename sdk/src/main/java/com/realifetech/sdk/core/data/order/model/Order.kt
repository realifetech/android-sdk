package com.realifetech.sdk.core.data.order.model

import android.os.Parcelable
import com.realifetech.sdk.core.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.shared.`object`.TimeSlot
import com.realifetech.type.CollectionPreferenceType
import com.realifetech.type.OrderStatus
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val id: String,
    val orderNumber: Int?,
    val qrCodeUrl: String?,
    val collectionDate: String?,
    val status: OrderStatus?,
    val state: OrderState?,
    val paymentStatus: String?,
    val grossAmount: Int?,
    val discountAmount: Int?,
    val netAmount: Int?,
    val estimatedAt: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val collectionPreferenceType: CollectionPreferenceType?,
    val seatInfo: SeatInfo?,
    val items: List<OrderItem?>?,
    val orderNotes: List<OrderNote?>?,
    val timeSlot: TimeSlot?,
    val fulfilmentPoint: FulfilmentPoint?,
    val user: User?
) : Parcelable