package com.realifetech.core_sdk.feature.widgets.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.GetWidgetsByScreenIdQuery
import com.realifetech.GetWidgetsByScreenTypeQuery
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.widgets.WidgetsRepository
import com.realifetech.core_sdk.feature.widgets.domain.Widget
import com.realifetech.fragment.FragmentWidget
import com.realifetech.type.ScreenType

class WidgetsBackendDataSource(private val apolloClient: ApolloClient) :
    WidgetsRepository.DataSource {

    override suspend fun getWidgetsByScreenType(screenType: ScreenType,pageSize:Int,page:Int): Result<List<Widget>> {
        return try {
            val response = apolloClient.query(GetWidgetsByScreenTypeQuery(screenType,pageSize,page)).await()
            response.data?.getWidgetsByScreenType?.fragments?.fragmentWidget.extractResponse(
                response.errors
            )
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getWidgetsByScreenId(id: String,
                                              pageSize: Int,
                                              page: Int): Result<List<Widget>> {
        return try {
            val response = apolloClient.query(GetWidgetsByScreenIdQuery(id,pageSize,page)).await()
            response.data?.getWidgetsByScreenId?.fragments?.fragmentWidget.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    private fun FragmentWidget?.extractResponse(errors: List<Error>?): Result<List<Widget>> {
        val extractedWidgets = this?.edges?.filterNotNull()
        return if (extractedWidgets != null) {
            Result.Success(extractedWidgets.toWidgets())
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