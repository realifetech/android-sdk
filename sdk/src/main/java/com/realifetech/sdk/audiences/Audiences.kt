package com.realifetech.sdk.audiences

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.BelongsToAudienceWithExternalIdQuery
import com.realifetech.sdk.core.utils.hasNetworkConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Audiences @Inject constructor(
    private val apolloClient: ApolloClient,
    private val context: Context
) {

    fun deviceIsMemberOfAudience(
        externalAudienceId: String,
        callback: (error: Error?, result: Boolean) -> Unit
    ) {
        if (!context.hasNetworkConnection) {
            callback(Error("No Internet connection"), false)
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            var errorToReturn: Error?
            var belongsToAudienceResponse = false

            try {
                val responseServer =
                    apolloClient.query(BelongsToAudienceWithExternalIdQuery(externalAudienceId))
                        .await()
                belongsToAudienceResponse =
                    responseServer.data?.me?.device?.belongsToAudienceWithExternalId ?: false
                val firstServerError = responseServer.errors?.firstOrNull()
                errorToReturn =
                    if (firstServerError != null) Error(firstServerError.message) else null
            } catch (exception: ApolloHttpException) {
                val errorMessage =
                    "HTTP error with code = ${exception.code()} & message = ${exception.message()}"
                errorToReturn = Error(errorMessage)
            }

            withContext(Dispatchers.Main) {
                callback(errorToReturn, belongsToAudienceResponse)
            }
        }
    }
}