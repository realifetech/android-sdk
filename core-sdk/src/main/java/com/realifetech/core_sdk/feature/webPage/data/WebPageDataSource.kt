package com.realifetech.core_sdk.feature.webPage.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.GetWebPageByTypeQuery
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.helper.extractResponse
import com.realifetech.core_sdk.feature.webPage.WebPageRepository
import com.realifetech.fragment.FragmentWebPage
import com.realifetech.type.WebPageType

class WebPageDataSource(private val apolloClient: ApolloClient) : WebPageRepository.DataSource {
    override suspend fun getWebPageByType(type: WebPageType): Result<FragmentWebPage> {
        return try {
            val response = apolloClient.query(GetWebPageByTypeQuery(type)).await()
            response.data?.getWebPageByType?.fragments?.fragmentWebPage.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }
}