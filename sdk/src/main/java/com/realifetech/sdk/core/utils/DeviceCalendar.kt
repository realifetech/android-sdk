package com.realifetech.sdk.core.utils

import java.util.*

interface DeviceCalendar {
    val time: Date
    fun add(field: Int, amount: Int)
    val currentTime: Long
}