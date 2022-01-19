package com.realifetech.sdk.campaignautomation.domain

import com.realifetech.sdk.campaignautomation.data.datasource.CampaignAutomationDataSource
import com.realifetech.sdk.campaignautomation.data.model.ContentResponse
import javax.inject.Inject

class CampaignAutomationRepository @Inject constructor(private val datasource: CampaignAutomationDataSource) {

    fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: ContentResponse?) -> Unit
    ) {
        datasource.getContentByExternalId(externalId, callback)
    }

}