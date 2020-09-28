package com.realifetech.sdk

import com.realifetech.sdk.general.General

object Realifetech {
    fun getGeneral(): General {
        return General.instance
    }
}