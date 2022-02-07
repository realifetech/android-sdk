package com.realifetech.sample.campaignAutomation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.realifetech.sample.R
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.campaignautomation.data.model.BannerDataModel
import com.realifetech.sdk.campaignautomation.data.model.Content
import com.realifetech.sdk.campaignautomation.data.model.RLTDataModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_campaign_automation_sample.*

class CampaignAutomationActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_automation_sample)
        val great = findViewById<ComposeView>(R.id.great)

        queryCampaignAutomation.setOnClickListener {
//            RealifeTech.getCampaignAutomation().factories(List<RLTFactory>)
            RealifeTech.getCampaignAutomation().fetch("homepage-top-view") { error, response ->
                response?.let {
                    great.setContent {
                        OnlyText(response)
                    }
                }
                error?.let {

                }
            }


        }
    }


    @Composable
    fun OnlyText(list: List<RLTDataModel>) {
        LazyColumn() {
            items(items = list) {
                BannerViewTextOnly(it)
            }
        }
    }

    @Composable
    fun OnlyImage(values: List<RLTDataModel>) {
        LazyColumn() {
            items(items = values) {
                BannerViewImageOnly(it)
            }
        }
    }

    @Composable
    fun BannerViewImageOnly(bannerDataModel: Any) {
        bannerDataModel as BannerDataModel
        Column() {
            Image(
                painter = rememberImagePainter(bannerDataModel.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
        }

    }

    @Composable
    fun BannerViewTextOnly(bannerDataModel: Any) {
        bannerDataModel as BannerDataModel
        Column() {
            bannerDataModel.title?.let { Text(text = it) }
            bannerDataModel.subtitle?.let { Text(text = it) }
        }

    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CampaignAutomationActivity::class.java))
        }
    }
}