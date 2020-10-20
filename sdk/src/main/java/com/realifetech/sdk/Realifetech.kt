package com.realifetech.sdk

import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.communicate.Communicate
import com.realifetech.sdk.general.General

object Realifetech {
    fun getGeneral(): General {
        return General.instance
    }

    fun getCommunicate(): Communicate {
        return Communicate.instance
    }

    fun getAnalytics(): Analytics {
        return Analytics.instance
    }
}