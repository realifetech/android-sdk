package com.realifetech.sdk.content.widgets.data

import com.apollographql.apollo.api.Error
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetWidgetsByScreenIdQuery
import com.realifetech.GetWidgetsByScreenTypeQuery
import com.realifetech.sdk.core.domain.Result
import com.realifetech.sdk.content.widgets.domain.WidgetsRepository
import com.realifetech.fragment.FragmentWidget
import com.realifetech.sdk.core.network.graphQl.GraphQlModule
import com.realifetech.type.ScreenType

class WidgetsBackendDataSource() :
    WidgetsRepository.DataSource {
    private val apolloClient = GraphQlModule.apolloClient

    override suspend fun getWidgetsByScreenType(
        screenType: ScreenType,
        pageSize: Int,
        page: Int
    ): Result<WidgetEdge> {
        return try {
            val response =
                apolloClient.query(GetWidgetsByScreenTypeQuery(screenType, pageSize, page))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                    .build()
                    .await()
            response.data?.getWidgetsByScreenType?.fragments?.fragmentWidget.extractResponse(
                response.errors
            )
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getWidgetsByScreenId(
        id: String,
        pageSize: Int,
        page: Int
    ): Result<WidgetEdge> {
        return try {
            val response = apolloClient.query(GetWidgetsByScreenIdQuery(id, pageSize, page))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
                .await()
            response.data?.getWidgetsByScreenId?.fragments?.fragmentWidget.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    private fun FragmentWidget?.extractResponse(errors: List<Error>?): Result<WidgetEdge> {
        val extractedWidgets = this?.edges?.filterNotNull()
        val nextPage = this?.nextPage
        return if (extractedWidgets != null) {
            Result.Success(WidgetEdge(extractedWidgets.toWidgets(), nextPage))
        } else {
            val errorMessage = errors?.firstOrNull()?.message ?: "Unknown error"
            Result.Error(RuntimeException(errorMessage))
        }
    }

    private fun List<FragmentWidget.Edge>.toWidgets(): List<Widget> {
        return map {
            val contentIds = it.variation?.contentIds.orEmpty().filterNotNull()
            val params = it.variation?.params.orEmpty().filterNotNull()
            val engagementParam = it.variation?.engagementParams.orEmpty().filterNotNull()
            val translationsTitle = it.variation?.translations.orEmpty().filterNotNull()
            Widget(
                id = it.id,
                style = it.style,
                viewAllUrl = it.viewAllUrl.orEmpty(),
                widgetType = it.widgetType,
                fetchType = it.variation?.fetchType,
                contentIds = contentIds,
                params = params,
                engagementParam = engagementParam,
                titleTranslations = translationsTitle
            )
        }
    }
}