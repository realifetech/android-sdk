package com.realifetech.sdk.audiences

import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.BelongsToAudienceWithExternalIdQuery
import com.realifetech.sdk.core.network.graphQl.GraphQlModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Audiences private constructor() {

    fun deviceIsMemberOfAudience(externalAudienceId: String, callback: (error: Error?, result: Boolean) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apolloClient = GraphQlModule.apolloClient
            var errorToReturn: Error?
            var belongsToAudienceResponse = false

            try {
                val responseServer =
                    apolloClient.query(BelongsToAudienceWithExternalIdQuery(externalAudienceId)).await()
                belongsToAudienceResponse = responseServer.data?.me?.device?.belongsToAudienceWithExternalId ?: false
                val firstServerError = responseServer.errors?.firstOrNull()
                errorToReturn = if (firstServerError != null) Error(firstServerError.message) else null
            } catch (exception: ApolloHttpException) {
                val errorMessage = "HTTP error with code = ${exception.code()} & message = ${exception.message()}"
                errorToReturn = Error(errorMessage)
            }

            withContext(Dispatchers.Main) {
                callback(errorToReturn, belongsToAudienceResponse)
            }
        }
    }

    private object Holder {
        val instance = Audiences()
    }

    companion object {
        val instance: Audiences by lazy { Holder.instance }
    }
}