package com.realifetech.sdk.communicate.data.model

import com.realifetech.GetMyDeviceNotificationConsentsQuery
import com.realifetech.fragment.NotificationConsentFragment
import com.realifetech.sdk.communicate.domain.model.ConsentStatus
import com.realifetech.sdk.communicate.domain.model.DeviceNotificationConsent
import com.realifetech.sdk.communicate.domain.model.NotificationConsent
import com.realifetech.sdk.communicate.domain.model.Translation


fun NotificationConsentFragment.toDomainModel(): NotificationConsent {
    return NotificationConsent(
        id = this.id,
        name = this.name,
        sortId = this.sortId,
        status = ConsentStatus.safeValueOf(this.status.rawValue) ?: ConsentStatus.DISABLED,
        translations = this.translations?.filterNotNull()?.map { it.toDomainModel() }
    )
}

fun NotificationConsentFragment.Translation.toDomainModel(): Translation {
    return Translation(
        name = this.name,
        language = this.language
    )
}

fun GetMyDeviceNotificationConsentsQuery.GetMyDeviceNotificationConsent.asDomainModel(): DeviceNotificationConsent {
    return DeviceNotificationConsent(
        id = this.id,
        enabled = this.enabled,
        consent = this.consent.fragments.notificationConsentFragment.toDomainModel()
    )
}
