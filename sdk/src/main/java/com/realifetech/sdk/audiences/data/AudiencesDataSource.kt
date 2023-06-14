package com.realifetech.sdk.audiences.data

interface AudiencesDataSource {
    suspend fun belongsToAudienceWithExternalId(externalAudienceId: String): Boolean
}