package com.realifetech.sdk.identity.data.model

sealed class RLTAliasType {

    object ExternalUserId : RLTAliasType()
    object AltExternalUserId : RLTAliasType()
    object TicketmasterAccountId : RLTAliasType()
    object TdcAccountId : RLTAliasType()
    object BleepAccountId : RLTAliasType()
    class Dynamic(val rawvalue: String) : RLTAliasType()

    fun convertAliasToString(): String {
        return when (this) {
            ExternalUserId -> EXTERNAL_USER_ID
            AltExternalUserId -> ALT_EXTERNAL_USER_ID
            TicketmasterAccountId -> TM_ACCOUNT_ID
            TdcAccountId -> TDC_ACCOUNT_ID
            BleepAccountId -> BLEEP_ACCOUNT_ID
            is Dynamic -> this.rawvalue
        }
    }

    companion object{
        private const val EXTERNAL_USER_ID = "EXTERNAL_USER_ID"
        private const val ALT_EXTERNAL_USER_ID = "ALT_EXTERNAL_USER_ID"
        private const val TM_ACCOUNT_ID = "TM_ACCOUNT_ID"
        private const val TDC_ACCOUNT_ID = "TDC_ACCOUNT_ID"
        private const val BLEEP_ACCOUNT_ID ="BLEEP_ACCOUNT_ID"
    }

}



