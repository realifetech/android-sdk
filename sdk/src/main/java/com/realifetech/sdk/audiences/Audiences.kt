package com.realifetech.sdk.audiences

import android.content.Context
import com.realifetech.sdk.audiences.repository.AudiencesRepository
import com.realifetech.sdk.core.utils.hasNetworkConnection
import kotlinx.coroutines.*
import javax.inject.Inject

class Audiences @Inject constructor(
    private val audiencesRepository: AudiencesRepository,
    private val dispatcherIO:CoroutineDispatcher,
    private val dispatcherMain:CoroutineDispatcher,
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
        audiencesRepository.belongsToAudienceWithExternalId(externalAudienceId) { error, result ->
            GlobalScope.launch(dispatcherIO) {
                withContext(dispatcherMain) {
                    callback(error, result)
                }
            }
        }
    }
}