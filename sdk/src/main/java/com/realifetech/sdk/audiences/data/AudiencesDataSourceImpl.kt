package com.realifetech.sdk.audiences.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.BelongsToAudienceWithExternalIdQuery
import javax.inject.Inject

class AudiencesDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    AudiencesDataSource {
    override fun belongsToAudienceWithExternalId(
        externalAudienceId: String,
        callback: (error: Error?, result: Boolean) -> Unit
    ) {
        try {
            val responseServer =
                apolloClient.query(BelongsToAudienceWithExternalIdQuery(externalAudienceId))
                    .toBuilder().responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()

            responseServer.enqueue(object :
                ApolloCall.Callback<BelongsToAudienceWithExternalIdQuery.Data>() {
                override fun onResponse(response: Response<BelongsToAudienceWithExternalIdQuery.Data>) {
                    response.errors?.first()?.let {
                        callback.invoke(Error(it.message), false)
                    } ?: run {
                        callback.invoke(
                            null,
                            response.data?.me?.device?.belongsToAudienceWithExternalId ?: false
                        )
                    }
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(Error(e.message), false)
                }

            })
        } catch (exception: ApolloHttpException) {
            val errorMessage =
                "HTTP error with code = ${exception.code()} & message = ${exception.message()}"
            callback.invoke(Error(errorMessage), false)

        }
    }
}