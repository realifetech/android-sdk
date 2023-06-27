package com.realifetech.sdk.core.network.oauthtokenhelpers

import io.reactivex.Observable

interface OAuthRefreshOrWaitActionGenerating {
    val refreshTokenOrWaitAction: Observable<Unit>?
}

/*class OAuthRefreshOrWaitActionGenerator(
    private val authorisationWorker: AuthorisationWorkable,
    private val oAuthTokenRefreshWatcher: OAuthTokenRefreshWatchable,
    private val authorisationStore: AuthorisationStoring
) : OAuthRefreshOrWaitActionGenerating {

    override val refreshTokenOrWaitAction: Observable<Unit>?
        get() {
            return if (authorisationStore.accessTokenValid) {
                null
            } else {
                oAuthTokenRefreshWatcher.ongoingTokenRefresh?.let { ongoingTokenRefresh ->
                    // We take the last one because we only care about the current refresh.
                    ongoingTokenRefresh
                        .takeLast(1)
                        .map { Unit }
                } ?: run {
                    oAuthTokenRefreshWatcher.updateRefreshingStatus(RefreshingStatus.REFRESHING)
                    val refresh = authorisationWorker.refreshAccessToken ?: authorisationWorker.requestInitialAccessToken
                    refresh
                        .takeLast(1)
                        .doOnNext { _ ->
                            oAuthTokenRefreshWatcher.updateRefreshingStatus(RefreshingStatus.VALID)
                        }
                        .doOnError { _ ->
                            oAuthTokenRefreshWatcher.updateRefreshingStatus(RefreshingStatus.INVALID)
                        }
                        .map { Unit }
                }
            }
        }
}*/
