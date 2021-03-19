package com.realifetech.core_sdk.feature.webPage.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.GetWebPageByTypeQuery
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.webPage.WebPageRepository
import com.realifetech.fragment.WebPage
import com.realifetech.type.WebPageType

class WebPageDataSource(private val apolloClient: ApolloClient) : WebPageRepository.DataSource {
    override suspend fun getWebPageByType(type: WebPageType): Result<WebPage> {
        return try {
            val response = apolloClient.query(GetWebPageByTypeQuery(type)).await()
            response.data?.getWebPageByType?.fragments?.webPage.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    private fun WebPage?.extractResponse(errors: List<Error>?): Result<WebPage> {
        return try {
            this?.let {
                Result.Success(this)
            } ?: run {
                val errorMessage = errors?.firstOrNull()?.message ?: "Unknown error"
                Result.Error(RuntimeException(errorMessage))
            }
        } catch (exception: ApolloException) {
            Result.Error(exception)
        }
    }
}