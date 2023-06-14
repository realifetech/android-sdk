package com.realifetech.sdk.audiences

import android.content.Context
import com.realifetech.sdk.audiences.repository.AudiencesRepository
import com.realifetech.sdk.core.utils.hasNetworkConnection
import javax.inject.Inject

class Audiences @Inject constructor(
    private val audiencesRepository: AudiencesRepository,
    private val context: Context
) {
    suspend fun deviceIsMemberOfAudience(externalAudienceId: String): Boolean {
        if (!context.hasNetworkConnection) {
            throw Exception("No Internet connection")
        }

        return audiencesRepository.belongsToAudienceWithExternalId(externalAudienceId)
    }
}