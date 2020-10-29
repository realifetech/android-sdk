package com.realifetech.sdk.audiences

import com.apollographql.apollo.coroutines.await
import com.realifetech.BelongsToAudienceByExternalIdQuery
import com.realifetech.sdk.core.network.graphQl.GraphQlModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Audiences private constructor() {

    fun deviceIsMemberOfAudience(audienceId: String, callback: (error: Error?, result: Boolean) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apolloClient = GraphQlModule.apolloClient
            val responseServer = apolloClient.query(BelongsToAudienceByExternalIdQuery(audienceId)).await()
            val belongsToAudienceResponse = responseServer.data?.me?.device?.belongsToAudienceByExternalId ?: false
            val firstServerError = responseServer.errors?.firstOrNull()
            val errorToReturn = if (firstServerError != null) Error(firstServerError.message) else null

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