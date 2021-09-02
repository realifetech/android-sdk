package com.realifetech.sdk.general.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class DeviceConfigurationTest {

    @Test
    fun getWebOrderingJourneyUrl() {
        val webOrderingUrl = RLTConfiguration.ORDERING_JOURNEY_URL
        assertEquals(DEFAULT_URL, webOrderingUrl)
    }

    @Test
    fun setWebOrderingJourneyUrl() {
        RLTConfiguration.ORDERING_JOURNEY_URL = DIFFERENT_URL
        val webOrderingUrl = RLTConfiguration.ORDERING_JOURNEY_URL
        assertEquals(DIFFERENT_URL, webOrderingUrl)
    }

    companion object {
        const val DEFAULT_URL = "https://ordering.realifetech.com/"
        const val DIFFERENT_URL = "https://us-ordering.realifetech.com/"
    }
}