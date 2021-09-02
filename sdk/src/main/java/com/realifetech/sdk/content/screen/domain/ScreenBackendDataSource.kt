package com.realifetech.sdk.content.screen.domain

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetScreenByIdQuery
import com.realifetech.GetScreenByScreenTypeQuery
import com.realifetech.sdk.content.screen.data.Translation
import com.realifetech.sdk.content.screen.data.mapToTranslation
import com.realifetech.sdk.core.utils.Result
import com.realifetech.type.ScreenType

class ScreenBackendDataSource (private val apolloClient: ApolloClient) :
    ScreensDataSource {
    override suspend fun getScreenByScreenType(screenType: ScreenType): Result<List<Translation>> {
        return try {
            val response = apolloClient.query(GetScreenByScreenTypeQuery(screenType))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
                .await()
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
            val response = apolloClient.query(GetScreenByIdQuery(id))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
                .await()
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