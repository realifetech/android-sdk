package com.realifetech.sdk.communicate.data.model

import com.realifetech.fragment.NotificationConsentFragment
import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.sdk.content.screen.data.model.mapToTranslation
import com.realifetech.type.ConsentStatus

data class NotificationConsent(
    val id: String,
    val name: String,
    val sortId: Int,
    val status: NotificationConsentStatus,
    val translations: List<Translation>
)

fun emptyNotificationConsent() = NotificationConsent(
    id = "",
    name = "",
    sortId = 0,
    status = NotificationConsentStatus.ACTIVE,
    translations = emptyList()
)

fun NotificationConsentFragment.asModel() = NotificationConsent(
    id = id,
    name = name,
    sortId = sortId,
    status = getNotificationConsentStatus(status.rawValue),
    translations = translations?.map { translation ->
        translation?.mapToTranslation() ?: Translation("", "")
    } ?: emptyList()
)

fun getNotificationConsentStatus(status: String): NotificationConsentStatus {
    return if (status == ConsentStatus.ACTIVE.toString()) {
        NotificationConsentStatus.ACTIVE
    } else {
        NotificationConsentStatus.DISABLED
    }
}

enum class NotificationConsentStatus {
    ACTIVE, DISABLED
}
