package com.realifetech.sdk.audiences.repository

import com.realifetech.sdk.audiences.data.AudiencesDataSource
import javax.inject.Inject

class AudiencesRepository @Inject constructor(private val audiencesDataSource: AudiencesDataSource) {
    suspend fun belongsToAudienceWithExternalId(externalAudienceId: String): Boolean {
        return audiencesDataSource.belongsToAudienceWithExternalId(externalAudienceId)
    }
}