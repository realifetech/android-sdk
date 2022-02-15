package com.realifetech.sdk.identity

import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.identity.domain.IdentityRepository
import com.realifetech.sdk.identity.sso.SSOFeature
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
    private val ssoFeature: SSOFeature,
    private val storage: AuthTokenStorage,
) {


    fun getSSO(): SSOFeature {
        return ssoFeature
    }

    fun logout() {
        webViewWrapper.clearCacheAndStorage()
    }

    fun identify(
        userId: String,
        traits: Map<String, Any>?,
        completion: (error: Exception?) -> Unit
    ) {
        RealifeTech.configuration.userId = userId
        RealifeTech.getAnalytics()
            .track(
                type = USER,
                action = IDENTIFY,
                new = traits,
                old = null
            ) {

            }
    }

    fun alias(
        aliasType: String,
        aliasId: String,
        completion: (error: Exception?) -> Unit
    ) {
        RealifeTech.getAnalytics()
            .track(
                type = USER,
                action = ALIAS,
                new = mapOf(aliasType to aliasId),
                old = null
            ) {

            }
    }

    fun clear() {
        RealifeTech.configuration.userId = null
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