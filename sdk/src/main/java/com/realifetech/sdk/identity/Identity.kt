package com.realifetech.sdk.identity

import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
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
        traits: Map<String, Any>?
    ){
        RealifeTech.configuration.userId = userId
//        RealifeTech.getAnalytics().logEvent()
    }

    fun alias(
        aliasType: String,
        aliasId: String
    ){
//        RealifeTech.getAnalytics().logEvent()
    }

    fun clear(){
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
}