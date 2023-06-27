package com.realifetech.sdk.core.network.oauthtokenhelpers

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface OAuthTokenRefreshWatchable {
    val ongoingTokenRefresh: Observable<Boolean>?
    fun updateRefreshingStatus(newValue: OAuthTokenStatus)
}

enum class OAuthTokenStatus {
    UNKNOWN, VALID, INVALID, REFRESHING
}

class OAuthTokenRefreshWatcher(
    tokenStatusSource: BehaviorSubject<OAuthTokenStatus> = BehaviorSubject.createDefault(OAuthTokenStatus.UNKNOWN)
) : OAuthTokenRefreshWatchable {

    private var status: BehaviorSubject<OAuthTokenStatus> = tokenStatusSource

    /*To monitor if the token is currently being refreshed, the Observable will emit a value of true
     when the state changes to VALID if the token is being refreshed. If the token is not being refreshed,
     it returns null.*/
    override val ongoingTokenRefresh: Observable<Boolean>?
        get() = if (status.value == OAuthTokenStatus.REFRESHING) {
            status.skip(1)
                .filter { it == OAuthTokenStatus.VALID }
                .map { it == OAuthTokenStatus.VALID }
        } else {
            null
        }

    override fun updateRefreshingStatus(newValue: OAuthTokenStatus) {
        if (status.value != newValue) {
            status.onNext(newValue)
        }
    }
}
