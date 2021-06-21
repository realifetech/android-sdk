package com.realifetech.core_sdk.data.order.model

import android.os.Parcelable
import com.realifetech.core_sdk.data.basket.model.CollectionPreferenceType
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.core_sdk.data.shared.`object`.TimeSlot
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
    val seatInfo: List<SeatInfo?>?,
    val items: List<OrderItem?>?,
    val orderNotes: List<OrderNote?>?,
    val timeSlot: TimeSlot?,
    val fulfilmentPoint: FulfilmentPoint?,
    val user: User?
) : Parcelable