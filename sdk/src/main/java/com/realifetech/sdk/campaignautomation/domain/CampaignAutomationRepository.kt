package com.realifetech.sdk.campaignautomation.domain

import com.realifetech.sdk.campaignautomation.data.datasource.CampaignAutomationDataSource
import com.realifetechCa.GetContentByExternalIdQuery
import javax.inject.Inject

class CampaignAutomationRepository @Inject constructor(private val datasource: CampaignAutomationDataSource) {

    fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: GetContentByExternalIdQuery.GetContentByExternalId?) -> Unit
    ) {
        datasource.getContentByExternalId(externalId, callback)
    }

}