package com.realifetech.core_sdk.feature.widgets.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.GetWidgetsByScreenTypeQuery
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.widgets.WidgetsRepository
import com.realifetech.core_sdk.feature.widgets.domain.Widget
import com.realifetech.type.ScreenType

class WidgetsBackendDataSource(private val apolloClient: ApolloClient) : WidgetsRepository.DataSource {

    override suspend fun getWidgetsForScreen(screenType: ScreenType): Result<List<Widget>> {
        return try {
            val response = apolloClient.query(GetWidgetsByScreenTypeQuery(screenType)).await()
            val extractedWidgets = response.data?.getWidgetsByScreenType?.edges?.filterNotNull()
            if (extractedWidgets != null) {
                Result.Success(extractedWidgets.toWidgets())
            } else {
                val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                Result.Error(RuntimeException(errorMessage))
            }
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    private fun List<GetWidgetsByScreenTypeQuery.Edge>.toWidgets(): List<Widget> {
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