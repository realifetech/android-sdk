package com.realifetech.sdk.analytics.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.realifetech.sdk.analytics.data.database.Entity.PendingAnalyticsEvent
import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PendingAnalyticEventsDatabaseTest {
    private lateinit var analyticEventsDao: PendingAnalyticEventsDao
    private lateinit var db: PendingAnalyticEventsDatabase
    private val event = AnalyticEventWrapper(
        "type",
        "action",
        null,
        null,
        System.currentTimeMillis()
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, PendingAnalyticEventsDatabase::class.java
        ).build()
        analyticEventsDao = db.pendingEventsDao()
    }


    @Test
    @Throws(Exception::class)
    fun saveAndRemoveAnalyticEvent() {
        analyticEventsDao.save(PendingAnalyticsEvent(event))
        val expectedResult = PendingAnalyticsEvent(event, 1)
        var actualResult = analyticEventsDao.getAll()
        Assert.assertEquals(expectedResult, actualResult.first())
        analyticEventsDao.remove(actualResult.first())
        actualResult = analyticEventsDao.getAll()
        assert(actualResult.isEmpty())
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}