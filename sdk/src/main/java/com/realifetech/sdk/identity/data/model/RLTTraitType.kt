package com.realifetech.sdk.identity.data.model

sealed class RLTTraitType {

    object Email : RLTTraitType()
    object FirstName : RLTTraitType()
    object LastName : RLTTraitType()
    object DateOfBirth : RLTTraitType()
    object PushConsent : RLTTraitType()
    object EmailConsent : RLTTraitType()
    class Dynamic(val rawvalue: String) : RLTTraitType()

}


