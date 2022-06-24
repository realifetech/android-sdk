package com.realifetech.sdk.analytics.domain

import com.realifetech.sdk.analytics.data.datasource.AnalyticsStorageDataSource
import com.realifetech.sdk.analytics.mocks.AnalyticsMocks.analyticEventWrapper
import com.realifetech.sdk.analytics.mocks.AnalyticsMocks.event1
import com.realifetech.sdk.analytics.mocks.AnalyticsMocks.events
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AnalyticsStorageTest {

    private lateinit var analyticsStorage: AnalyticsStorage
    private lateinit var dataSource: AnalyticsStorageDataSource

    @Before
    fun setUp() {
        dataSource = mockk()
        analyticsStorage = AnalyticsStorage(dataSource)
    }

    @Test
    fun `get event was called and returns data`() {
        every { dataSource.getAll() } returns events
        val result = analyticsStorage.getAll()
        verify { dataSource.getAll() }
        Assert.assertEquals(events, result)
    }

    @Test
    fun `save event was called and generates pendingEvent successfully`() {
        every { dataSource.save(analyticEventWrapper) } just Runs
        analyticsStorage.save(analyticEventWrapper)
        verify { dataSource.save(analyticEventWrapper) }
    }

    @Test
    fun `remove event was called and returns data`() {
        every { dataSource.remove(event1) } just Runs
        analyticsStorage.remove(event1)
        verify { dataSource.remove(event1) }
    }
}