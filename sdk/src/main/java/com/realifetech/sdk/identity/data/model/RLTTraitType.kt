package com.realifetech.sdk.identity.data.model

sealed class RLTTraitType {

    object Email : RLTTraitType()
    object FirstName : RLTTraitType()
    object LastName : RLTTraitType()
    object DateOfBirth : RLTTraitType()
    object PushConsent : RLTTraitType()
    object EmailConsent : RLTTraitType()
    class Dynamic(val rawvalue: String) : RLTTraitType()

    internal fun convertTraitToString(): String {
        return when (this) {
            DateOfBirth -> DOB
            Email -> EMAIL
            EmailConsent -> EMAIL_CONSENT
            FirstName -> FIRST_NAME
            LastName -> LAST_NAME
            PushConsent -> PUSH_CONSENT
            is Dynamic -> this.rawvalue
        }
    }

    companion object {
        private const val DOB = "dateOfBirth"
        private const val EMAIL = "email"
        private const val EMAIL_CONSENT = "emailConsent"
        private const val FIRST_NAME = "firstName"
        private const val LAST_NAME = "lastName"
        private const val PUSH_CONSENT = "pushConsent"
    }

}


