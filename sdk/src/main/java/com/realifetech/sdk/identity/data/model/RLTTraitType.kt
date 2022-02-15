package com.realifetech.sdk.identity.data.model

enum class RLTTraitType(val aliasType: String) {
    EMAIL("email"),
    FIRSTNAME("firstName"),
    LASTNAME("lastName"),
    DATEOFBIRTH("dateOfBirth"),
    PUSHCONSENT("pushConsent"),
    EMAILCONSENT("emailConsent")

}

fun dynamic(value: String): String {
    return value
}


