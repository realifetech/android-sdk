package com.realifetech.sdk.campaignautomation.data.datasource

import com.realifetech.sdk.campaignautomation.data.model.ContentResponse

interface CampaignAutomationDataSource {

    fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: ContentResponse?) -> Unit
    )

}