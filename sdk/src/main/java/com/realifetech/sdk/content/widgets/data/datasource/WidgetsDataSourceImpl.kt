package com.realifetech.sdk.content.widgets.data.datasource

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.realifetech.GetWidgetsByScreenIdQuery
import com.realifetech.GetWidgetsByScreenTypeQuery
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.sdk.content.widgets.data.model.asModel
import com.realifetech.type.ScreenType
import kotlinx.coroutines.coroutineScope

class WidgetsDataSourceImpl(private val apolloClient: ApolloClient) :
    WidgetsDataSource {


    override suspend fun getWidgetsByScreenType(
        screenType: ScreenType,
        pageSize: Int,
        page: Int
    ): Result<WidgetEdge> = coroutineScope {
        try {
            val response = apolloClient.query(GetWidgetsByScreenTypeQuery(screenType, pageSize, page)).execute()

            if (response.hasErrors()) {
                Result.failure(Exception("Error fetching widgets by screen type"))
            } else {
                response.data?.getWidgetsByScreenType?.fragmentWidget?.let { fragment ->
                    Result.success(
                        WidgetEdge(
                            fragment.edges?.filterNotNull()?.map { it.asModel() },
                            fragment.nextPage
                        )
                    )
                } ?: Result.failure(Exception("No widgets found for screen type"))
            }
        } catch (exception: ApolloException) {
            Result.failure(exception)
        }
    }

    override suspend fun getWidgetsByScreenId(
        id: String,
        pageSize: Int,
        page: Int
    ): Result<WidgetEdge> = coroutineScope {
        try {
            val response = apolloClient.query(GetWidgetsByScreenIdQuery(id, pageSize, page)).execute()

            if (response.hasErrors()) {
                Result.failure(Exception("Error fetching widgets by screen id"))
            } else {
                response.data?.getWidgetsByScreenId?.fragmentWidget?.let { fragment ->
                    Result.success(
                        WidgetEdge(
                            fragment.edges?.filterNotNull()?.map { it.asModel() },
                            fragment.nextPage
                        )
                    )
                } ?: Result.failure(Exception("No widgets found for screen id"))
            }
        } catch (exception: ApolloException) {
            Result.failure(exception)
        }
    }
}