package com.realifetech.sdk.general.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class DeviceConfigurationTest {

    @Test
    fun getWebOrderingJourneyUrl() {
        val webOrderingUrl = DeviceConfiguration().webOrderingJourneyUrl
        assertEquals(DEFAULT_URL, webOrderingUrl)
    }

    @Test
    fun setWebOrderingJourneyUrl() {
        val configuration = DeviceConfiguration()
        configuration.webOrderingJourneyUrl = DIFFERENT_URL
        val webOrderingUrl = configuration.webOrderingJourneyUrl
        assertEquals(DIFFERENT_URL, webOrderingUrl)
    }

    companion object {
        const val DEFAULT_URL = "https://ordering.realifetech.com/"
        const val DIFFERENT_URL = "https://us-ordering.realifetech.com/"
    }
}