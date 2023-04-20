package com.realifetech.sdk.communicate.domain.model

data class NotificationConsent(
    val id: String,
    val name: String,
    val sortId: Int,
    val status: ConsentStatus,
    val translations: List<Translation>?
)
