package com.realifetech.sdk.core.utils

import java.util.*

class DeviceCalendarWrapper : DeviceCalendar {


    private var calendar: Calendar = Calendar.getInstance()

    override val time: Date
        get() = calendar.time

    override fun add(field: Int, amount: Int) {
        calendar.add(field, amount)
    }

    override val currentTime: Long
        get() = calendar.timeInMillis

}