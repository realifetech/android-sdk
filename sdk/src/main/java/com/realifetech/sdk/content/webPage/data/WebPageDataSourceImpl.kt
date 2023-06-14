package com.realifetech.sdk.content.webPage.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.realifetech.GetWebPageByTypeQuery
import com.realifetech.fragment.FragmentWebPage
import com.realifetech.type.WebPageType
import timber.log.Timber

class WebPageDataSourceImpl (private val apolloClient: ApolloClient) :
    WebPageDataSource {

    @ApolloExperimental
    override suspend fun getWebPageByType(type: WebPageType): FragmentWebPage? {
        return try {
            val response = apolloClient.query(GetWebPageByTypeQuery(type)).execute()
            response.data?.getWebPageByType?.fragmentWebPage
        } catch (exception: Exception) {
            Timber.e(exception, "Error getting web page by type")
            null
        }
    }
}