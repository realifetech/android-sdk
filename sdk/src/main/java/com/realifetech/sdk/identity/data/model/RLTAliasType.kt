package com.realifetech.sdk.identity.data.model

sealed class RLTAliasType {

    object ExternalUserId : RLTAliasType()
    object AltExternalUserId : RLTAliasType()
    object TicketmasterAccountId : RLTAliasType()
    object TdcAccountId : RLTAliasType()
    object BleepAccountId : RLTAliasType()
    class Dynamic(val rawvalue: String) : RLTAliasType()

}
