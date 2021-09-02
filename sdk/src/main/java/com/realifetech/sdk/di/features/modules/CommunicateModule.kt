package com.realifetech.sdk.di.features.modules

import android.content.Context
import com.realifetech.sdk.communicate.domain.PushNotificationStorage
import com.realifetech.sdk.communicate.domain.PushNotificationsTokenPreferenceStorage
import dagger.Module
import dagger.Provides

@Module
object CommunicateModule {

    @Provides
    internal fun pushNotificationsTokenPreferenceStorage(context: Context): PushNotificationStorage {
        return PushNotificationsTokenPreferenceStorage(context)
    }
}