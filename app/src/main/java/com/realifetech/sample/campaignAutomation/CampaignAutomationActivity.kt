package com.realifetech.sample.campaignAutomation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import com.realifetech.sdk.campaignautomation.data.model.RLTFetcher
import com.realifetechCa.GetContentByExternalIdQuery
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_campaign_automation_sample.*

class CampaignAutomationActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_automation_sample)
        val great = findViewById<ComposeView>(R.id.great)
        queryCampaignAutomation.setOnClickListener {

            Single.just(
                RealifeTech.getCampaignAutomation()
                    .getContentByExternalId("homepage-top-view") { error, result ->
                        addSubscription(getData(error, result)?.subscribe(
                            {
                                val fetcher = RLTFetcher(result)
                                val list = fetcher.returnList()
                                great.setContent {
                                    BannerListView(list)
                                }
                            }, {
                                Toast.makeText(
                                    this,
                                    "${this.getString(R.string.error_in_loading)}${error?.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            })
                        )
                    })
        }
    }

    @Composable
    fun BannerListView(values: List<BannerDataModel>) {
        LazyColumn() {
            items(items = values) {
                BannerView(it)
            }
        }
    }

    @Composable
    fun BannerView(bannerDataModel: BannerDataModel) {
        Column() {
            bannerDataModel.title?.let { Text(text = it) }
            bannerDataModel.subtitle?.let { Text(text = it) }
            bannerDataModel.language?.let { Text(text = it) }
            Image(
                painter = rememberImagePainter(bannerDataModel.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
        }

    }

    private fun addSubscription(disposable: Disposable?) {
        disposable?.let { compositeDisposable.add(disposable) }
    }

    private fun getData(
        error: Exception?,
        result: GetContentByExternalIdQuery.GetContentByExternalId?
    ): Observable<GetContentByExternalIdQuery.GetContentByExternalId>? {
        return error?.let { exception ->
            Observable.error(exception)
        } ?: result?.run {
            Observable.just(this)
        }?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CampaignAutomationActivity::class.java))
        }
    }
}