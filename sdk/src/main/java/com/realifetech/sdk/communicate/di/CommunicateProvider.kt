package com.realifetech.sdk.communicate.di

import com.realifetech.sdk.communicate.network.CommunicationApiNetwork
import com.realifetech.sdk.communicate.network.CommunicationApiService

object CommunicateProvider {
    internal fun provideApiService(): CommunicationApiService {
        return CommunicationApiNetwork.get()
    }
}