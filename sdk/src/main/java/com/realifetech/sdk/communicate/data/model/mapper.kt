package com.realifetech.sdk.communicate.data.model

import com.realifetech.fragment.NotificationConsentFragment
import com.realifetech.sdk.communicate.domain.model.ConsentStatus
import com.realifetech.sdk.communicate.domain.model.NotificationConsent
import com.realifetech.sdk.communicate.domain.model.Translation

fun NotificationConsentFragment.toDomainModel(): NotificationConsent {
    return NotificationConsent(
        id = this.id,
        name = this.name,
        sortId = this.sortId,
        status = ConsentStatus.safeValueOf(this.status.rawValue) ?: ConsentStatus.DISABLED,
        translations = this.translations?.mapNotNull {
            it?.let {
                Translation(it.name, it.language)
            }
        }
    )
}