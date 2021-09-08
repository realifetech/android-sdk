package com.realifetech.sdk.analytics

import com.realifetech.sdk.analytics.domain.AnalyticsEngine
import com.realifetech.sdk.analytics.domain.AnalyticsStorage
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class AnalyticsTest {

    @RelaxedMockK
    lateinit var analyticsStorage: AnalyticsStorage

    @RelaxedMockK
    lateinit var analyticsEngine: AnalyticsEngine
    lateinit var analytics: Analytics

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        analytics = Analytics(analyticsEngine, analyticsStorage)
    }

    @Test
    fun logEvent() {x
    }
}