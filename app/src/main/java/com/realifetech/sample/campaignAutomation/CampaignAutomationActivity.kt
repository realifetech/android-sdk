package com.realifetech.sample.campaignAutomation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.realifetech.sample.R
import io.reactivex.disposables.CompositeDisposable

class CampaignAutomationActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_campaign_automation_sample)
        setContent {
            Banana()
        }

//        queryCampaignAutomation.setOnClickListener {
//
//            Single.just(
//                RealifeTech.getCampaignAutomation()
//                    .getContentByExternalId("homepage-top-view") { error, result ->
//                        addSubscription(getData(error, result)?.subscribe(
//                            {
//                                 RLTFetcher(result)
//                            }, {
//                                Toast.makeText(
//                                    this,
//                                    "${this.getString(R.string.error_in_loading)}${error?.message}",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                            })
//                        )
//                    })
//        }
    }

    @Composable
    fun Banana() {
        Text(text = "HI THERE")
    }

//    private fun addSubscription(disposable: Disposable?) {
//        disposable?.let { compositeDisposable.add(disposable) }
//    }

//    private fun getData(
//        error: Exception?,
//        result: GetContentByExternalIdQuery.GetContentByExternalId?
//    ): Observable<GetContentByExternalIdQuery.GetContentByExternalId>? {
//        return error?.let { exception ->
//            Observable.error(exception)
//        } ?: result?.run {
//            Observable.just(this)
//        }?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
//    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CampaignAutomationActivity::class.java))
        }
    }
}