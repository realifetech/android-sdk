package com.realifetech.sdk.content.screen.data.datasource

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloHttpException
import com.realifetech.GetScreenByIdQuery
import com.realifetech.GetScreenByScreenTypeQuery
import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.sdk.content.screen.data.model.mapToTranslation
import com.realifetech.type.ScreenType

class ScreenDataSourceImpl(private val apolloClient: ApolloClient) :
    ScreensDataSource {
    override suspend fun getScreenByScreenType(screenType: ScreenType): List<Translation> {
        return try {
            val response = apolloClient.query(GetScreenByScreenTypeQuery(screenType)).execute()
            response.data?.getScreenByScreenType?.translations?.map { result -> result.mapToTranslation() }
                ?: listOf()
        } catch (exception: ApolloHttpException) {
            listOf()
        }
    }
    override suspend fun getScreenById(id: String): List<Translation> {
        return try {
            val response = apolloClient.query(GetScreenByIdQuery(id)).execute()
            response.data?.getScreenById?.translations?.map { result -> result.mapToTranslation() }
                ?: listOf()
        } catch (exception: ApolloHttpException) {
            listOf()
        }
    }
}