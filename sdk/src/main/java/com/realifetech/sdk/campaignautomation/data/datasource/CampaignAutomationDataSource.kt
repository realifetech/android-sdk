package com.realifetech.sdk.campaignautomation.data.datasource

import com.realifetechCa.GetContentByExternalIdQuery

interface CampaignAutomationDataSource {

    fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: GetContentByExternalIdQuery.GetContentByExternalId?) -> Unit
    )

}