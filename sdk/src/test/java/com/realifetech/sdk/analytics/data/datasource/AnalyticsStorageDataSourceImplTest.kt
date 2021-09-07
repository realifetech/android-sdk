package com.realifetech.sdk.analytics.data.datasource

import com.realifetech.sdk.analytics.data.database.PendingAnalyticEventsDao
import com.realifetech.sdk.analytics.mocks.AnalayticsMocks.analyticsEvent1
import com.realifetech.sdk.analytics.mocks.AnalayticsMocks.event1
import com.realifetech.sdk.analytics.mocks.AnalayticsMocks.events
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AnalyticsStorageDataSourceImplTest {

    private lateinit var analyticsStorageDataSource: AnalyticsStorageDataSourceImpl
    private lateinit var myDao: PendingAnalyticEventsDao

    @Before
    fun setUp() {
        myDao = mockk()
        analyticsStorageDataSource = AnalyticsStorageDataSourceImpl(myDao)
    }

    @Test
    fun `get event was called and returns data`() {
        every { myDao.getAll() } returns events
        val result = analyticsStorageDataSource.getAll()
        verify { myDao.getAll() } // Test was called once
        Assert.assertEquals(events, result)
    }

    @Test
    fun `save event was called and generates pendingEvent successfully`() {
        every { myDao.save(event1) } just Runs
        analyticsStorageDataSource.save(analyticsEvent1)
        verify { myDao.save(event1) } // Test was called once
    }

    @Test
    fun `remove event was called and returns data`() {
        every { myDao.remove(event1) } just Runs
        analyticsStorageDataSource.remove(event1)
        verify { myDao.remove(event1) } // Test was called once
    }
}