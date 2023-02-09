package com.realifetech.sdk.identity

import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.identity.data.model.RLTAliasType
import com.realifetech.sdk.identity.data.model.RLTTraitType
import com.realifetech.sdk.identity.domain.IdentityRepository
import com.realifetech.sdk.sell.weboredering.WebViewWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun getSSO(provider:String,callback: (error: Exception?, url: String?) -> Unit){
        identityRepository.getSSO(provider,callback)
    }
    fun identify(
        userId: String,
        traits: Map<RLTTraitType, Any>?,
        completion: (error: Exception?, result: Boolean) -> Unit
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
            old = null,
            completion
        )
    }

    fun deleteMyAccount(callback: (error: Exception?, success: Boolean?) -> Unit) =
        identityRepository.deleteMyAccount(callback)

    fun alias(
        aliasType: RLTAliasType,
        aliasId: String,
        completion: (error: Exception?, result: Boolean) -> Unit
    ) {

        val alias = aliasType.convertAliasToString()

        analytics.track(
            type = USER,
            action = ALIAS,
            new = mapOf(alias to aliasId),
            old = null,
            completion
        )
    }

    fun clear() {
        configurationStorage.userId = null
    }


    fun attemptLogin(
        emailAddress: String, firstName: String?, lastName: String?, salt: String,
        completion: (error: Exception?) -> Unit
    ) {
        GlobalScope.launch(dispatcherIO) {
            identityRepository.attemptToLogin(
                emailAddress,
                firstName,
                lastName,
                salt
            ) { authToken, errorMessage ->
                GlobalScope.launch(dispatcherIO) {
                    withContext(dispatcherMain) {
                        errorMessage?.let {
                            completion.invoke(it)
                        }
                        authToken?.let {
                            webViewWrapper.webView?.apply {
                                webViewWrapper.authenticate(it)
                            } ?: run {
                                storage.webAuthToken = it
                            }
                            completion.invoke(null)
                        }
                    }

                }

            }


        }

    }

    companion object {
        private const val USER = "user"
        private const val ALIAS = "alias"
        private const val IDENTIFY = "identify"
    }
}