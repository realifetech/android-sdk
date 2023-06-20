package com.realifetech.sdk.di.features.modules

import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.identity.data.IdentityDataSourceImpl
import com.realifetech.sdk.identity.domain.IdentityRepository
import dagger.Module
import dagger.Provides

@Module
object IdentityModule {

    @Provides
    fun identityRepository(dataSource: IdentityDataSourceImpl, authTokenStorage: AuthTokenStorage) =
        IdentityRepository(dataSource, authTokenStorage)
}