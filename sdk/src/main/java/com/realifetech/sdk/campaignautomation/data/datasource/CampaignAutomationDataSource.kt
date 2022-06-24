package com.realifetech.sdk.campaignautomation.data.datasource

import com.realifetech.GetContentByExternalIdQuery

interface CampaignAutomationDataSource {

    fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: GetContentByExternalIdQuery.GetContentByExternalId?) -> Unit
    )

}