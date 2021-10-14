package com.realifetech.sdk.audiences.repository

import com.realifetech.sdk.audiences.data.AudiencesDataSource
import javax.inject.Inject

class AudiencesRepository @Inject constructor(private val audiencesDataSource: AudiencesDataSource) {


    fun belongsToAudienceWithExternalId(
        externalAudienceId: String,
        callback: (error: Error?, result: Boolean) -> Unit
    ) {
        audiencesDataSource.belongsToAudienceWithExternalId(externalAudienceId, callback)
    }
}