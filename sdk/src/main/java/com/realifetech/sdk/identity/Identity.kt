package com.realifetech.sdk.identity

import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.identity.data.model.RLTAliasType
import com.realifetech.sdk.identity.data.model.RLTTraitType
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
        traits: Map<RLTTraitType, Any>?,
        completion: (error: Exception?, result: Boolean) -> Unit
    ) {

        RealifeTech.configuration.userId = userId

        val map = mutableMapOf<String, Any>()
        traits?.forEach { trait ->
            when (val key = trait.key) {
                RLTTraitType.DateOfBirth -> map["dateOfBirth"] = trait.value
                RLTTraitType.Email -> map["email"] = trait.value
                RLTTraitType.EmailConsent -> map["emailConsent"] = trait.value
                RLTTraitType.FirstName -> map["firstName"] = trait.value
                RLTTraitType.LastName -> map["lastName"] = trait.value
                RLTTraitType.PushConsent -> map["pushConsent"] = trait.value
                is RLTTraitType.Dynamic -> map[key.rawvalue] = trait.value
            }
        }

        RealifeTech.getAnalytics()
            .track(
                type = USER,
                action = IDENTIFY,
                new = map,
                old = null,
                completion
            )
    }

    fun alias(
        aliasType: RLTAliasType,
        aliasId: String,
        completion: (error: Exception?, result: Boolean) -> Unit
    ) {


        val alias = when(aliasType){
            RLTAliasType.ExternalUserId -> "EXTERNAL_USER_ID"
            RLTAliasType.AltExternalUserId -> "ALT_EXTERNAL_USER_ID"
            RLTAliasType.TicketmasterAccountId -> "TM_ACCOUNT_ID"
            RLTAliasType.TdcAccountId -> "TDC_ACCOUNT_ID"
            RLTAliasType.BleepAccountId -> "BLEEP_ACCOUNT_ID"
            is RLTAliasType.Dynamic -> aliasType.rawvalue
        }

        RealifeTech.getAnalytics()
            .track(
                type = USER,
                action = ALIAS,
                new = mapOf(alias to aliasId),
                old = null,
                completion
            )
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