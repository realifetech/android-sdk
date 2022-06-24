package com.realifetech.sdk.audiences.data

interface AudiencesDataSource {

    fun belongsToAudienceWithExternalId(
        externalAudienceId: String,
        callback: (error: Error?, result: Boolean) -> Unit
    )
}