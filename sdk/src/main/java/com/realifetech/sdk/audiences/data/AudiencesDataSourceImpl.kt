package com.realifetech.sdk.audiences.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.realifetech.BelongsToAudienceWithExternalIdQuery
import timber.log.Timber
import javax.inject.Inject

class AudiencesDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    AudiencesDataSource {
    override suspend fun belongsToAudienceWithExternalId(externalAudienceId: String): Boolean {
        return try {
            val query = BelongsToAudienceWithExternalIdQuery(externalAudienceId)
            val response = apolloClient.query(query).execute()

            if (response.hasErrors()) {
                val message = response.errors?.first()?.message
                Timber.e("Error in response: $message")
                throw ApolloException(message)
            }

            response.data?.me?.device?.belongsToAudienceWithExternalId ?: false
        } catch (exception: ApolloException) {
            Timber.e(exception, "ApolloException occurred")
            throw exception
        } catch (exception: Exception) {
            Timber.e(exception, "Unexpected exception occurred")
            throw exception
        }
    }
}