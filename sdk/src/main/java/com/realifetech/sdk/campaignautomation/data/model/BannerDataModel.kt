package com.realifetech.sdk.campaignautomation.data.model

import android.os.Parcelable
import android.util.Log
import com.realifetech.sdk.analytics.Analytics
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
data class BannerDataModel(
    val id: Int,
    val title: String?,
    val subtitle: String?,
    private val url: String?,
    val imageUrl: String?,
    val language: String?,
) : Parcelable, RLTLinkHandling, RLTDataModel {

    @IgnoredOnParcel
    private var analytics: Analytics? = null

    @IgnoredOnParcel
    private var campaignId: String? = null

    @IgnoredOnParcel
    private var location: String? = null


    override fun generateLinkHandler(): String? {
        val map = mutableMapOf<String, String>()
        this.campaignId?.also { map[CAMPAIGN_ID] = it }
        this.location?.also { map[EXTERNAL_ID] = it }
        map[CONTENT_ID] = id.toString()
        map[CONTENT_TYPE] = "BANNER"
        map[LANGUAGE_CODE] = language.toString()

        analytics?.let {
            it.track(
                "user",
                "interactWithContent",
                map,
                null
            ){error, result ->
                result.let {

                }
                error?.let {
                    Log.e(this.javaClass.name, "Error while sending Interact CA analytics")
                }
            }
        }


        return this.url
    }

    fun setDataForAnalytics(analytics: Analytics, campaignId: String, location: String) {
        this.analytics = analytics
        this.campaignId = campaignId
        this.location = location
    }

    companion object {
        private const val CAMPAIGN_ID = "campaignId"
        private const val EXTERNAL_ID = "externalId"
        private const val CONTENT_ID = "contentId"
        private const val CONTENT_TYPE = "contentType"
        private const val LANGUAGE_CODE = "languageCode"
        private const val USER = "user"
        private const val LOAD_CONTENT = "loadContent"
    }
}
