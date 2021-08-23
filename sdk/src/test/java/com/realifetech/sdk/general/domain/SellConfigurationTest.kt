package com.realifetech.sdk.general.domain

import com.realifetech.sdk.sell.domain.SellConfiguration
import org.junit.Assert.assertEquals
import org.junit.Test

class SellConfigurationTest {

    @Test
    fun getWebOrderingJourneyUrl() {
        val webOrderingUrl = SellConfiguration().webOrderingJourneyUrl
        assertEquals(DEFAULT_URL, webOrderingUrl)
    }

    @Test
    fun setWebOrderingJourneyUrl() {
         val configuration= SellConfiguration()
        configuration.webOrderingJourneyUrl= DIFFERENT_URL
        val webOrderingUrl =configuration.webOrderingJourneyUrl
        assertEquals(DIFFERENT_URL, webOrderingUrl)
    }

    companion object {
        const val DEFAULT_URL = "https://ordering.realifetech.com/"
        const val DIFFERENT_URL="https://us-ordering.realifetech.com/"
    }
}