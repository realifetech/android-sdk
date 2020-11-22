package com.realifetech.sdk.communicate.di

import com.realifetech.sdk.communicate.data.PushNotificationsTokenPreferenceStorage
import com.realifetech.sdk.communicate.data.PushNotificationsTokenStorage
import com.realifetech.sdk.communicate.network.CommunicationApiNetwork
import com.realifetech.sdk.communicate.network.CommunicationApiService

object CommunicateProvider {
    internal fun provideApiService(): CommunicationApiService {
        return CommunicationApiNetwork.get()
    }

    internal val tokenStorage: PushNotificationsTokenStorage
        get() {
            return PushNotificationsTokenStorage(PushNotificationsTokenPreferenceStorage())
        }
}