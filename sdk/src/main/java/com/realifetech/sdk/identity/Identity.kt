package com.realifetech.sdk.identity

import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.identity.data.model.RLTAliasType
import com.realifetech.sdk.identity.data.model.RLTTraitType
import com.realifetech.sdk.identity.domain.IdentityRepository
import com.realifetech.sdk.sell.weboredering.WebViewWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class Identity @Inject constructor(
    private val webViewWrapper: WebViewWrapper,
    private val identityRepository: IdentityRepository,
    private val dispatcherIO: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher,
    private val storage: AuthTokenStorage,
    private val configurationStorage: ConfigurationStorage,
    private val analytics: Analytics
) {

    fun logout() {
        webViewWrapper.clearCacheAndStorage()
    }

    suspend fun getSSO(provider:String): String? {
        return identityRepository.getSSO(provider)
    }
    suspend fun identify(
        userId: String,
        traits: Map<RLTTraitType, Any>?
    ) {

        configurationStorage.userId = userId

        val map = mutableMapOf<String, Any>()
        traits?.forEach { trait ->
            map[trait.key.convertTraitToString()] = trait.value
        }

        analytics.track(
            type = USER,
            action = IDENTIFY,
            new = map,
            old = null
        )
    }

    suspend fun deleteMyAccount() =
        identityRepository.deleteMyAccount()

    suspend fun alias(
        aliasType: RLTAliasType,
        aliasId: String
    ) {

        val alias = aliasType.convertAliasToString()

        analytics.track(
            type = USER,
            action = ALIAS,
            new = mapOf(alias to aliasId),
            old = null
        )
    }

    fun clear() {
        configurationStorage.userId = null
    }


    suspend fun attemptLogin(
        emailAddress: String, firstName: String?, lastName: String?, salt: String
    ) {
        val result = identityRepository.attemptToLogin(emailAddress, firstName, lastName, salt)
        when (result) {
            is com.realifetech.sdk.core.utils.Result.Success -> {
                val authToken = result.data
                authToken?.let {
                    webViewWrapper.webView?.apply {
                        webViewWrapper.authenticate(it)
                    } ?: run {
                        storage.webAuthToken = it
                    }
                }
            }
            is com.realifetech.sdk.core.utils.Result.Error -> throw result.exception
        }
    }

    companion object {
        private const val USER = "user"
        private const val ALIAS = "alias"
        private const val IDENTIFY = "identify"
    }
}