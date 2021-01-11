package com.realifetech.core_sdk.feature.screen.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.GetScreenByIdQuery
import com.realifetech.GetScreenByScreenTypeQuery
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.screen.ScreenRepository
import com.realifetech.core_sdk.feature.screen.domain.Translation
import com.realifetech.core_sdk.feature.screen.domain.mapToTranslation
import com.realifetech.type.ScreenType

class ScreenBackendDataSource(private val apolloClient: ApolloClient) :
    ScreenRepository.DataSource {
    override suspend fun getScreenByScreenType(screenType: ScreenType): Result<List<Translation>> {
        return try {
            val response = apolloClient.query(GetScreenByScreenTypeQuery(screenType)).await()
            response.data?.getScreenByScreenType?.let {
                Result.Success(it.translations.map { result -> result.mapToTranslation() })
            } ?: run {
                val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                Result.Error(RuntimeException(errorMessage))
            }
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getScreenById(id: String): Result<List<Translation>> {
        return try {
            val response = apolloClient.query(GetScreenByIdQuery(id)).await()
            response.data?.getScreenById?.let {
                Result.Success(it.translations.map { result -> result.mapToTranslation() })
            } ?: run {
                val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                Result.Error(RuntimeException(errorMessage))
            }
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }
}