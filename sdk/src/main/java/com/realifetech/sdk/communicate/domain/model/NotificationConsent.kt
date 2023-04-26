package com.realifetech.sdk.communicate.domain.model

data class NotificationConsent(
    val id: String,
    val name: String,
    val sortId: Int,
    val status: ConsentStatus,
    val translations: List<Translation>?
) {
    companion object {
        fun empty(): NotificationConsent {
            return NotificationConsent(
                id = "",
                name = "",
                sortId = 0,
                status = ConsentStatus.DISABLED,
                translations = null
            )
        }
    }
}

enum class ConsentStatus(val rawValue: String) {
    ACTIVE("ACTIVE"),
    DISABLED("DISABLED");

    companion object {
        fun safeValueOf(rawValue: String): ConsentStatus? =
            values().find { it.rawValue == rawValue }
    }
}

data class Translation(
    val name: String,
    val language: String
)