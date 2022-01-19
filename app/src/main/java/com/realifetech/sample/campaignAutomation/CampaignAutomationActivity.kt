package com.realifetech.sample.campaignAutomation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.realifetech.sample.R
import com.realifetech.sample.webPage.WebPageSampleActivity
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.campaignautomation.data.model.ContentResponse
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


        queryCampaignAutomation.setOnClickListener {

            Single.just(
                RealifeTech.getCampaignAutomation()
                    .getContentByExternalId("homepage-top-view") { error, result ->
                        addSubscription(getData(error, result)?.subscribe(
                            {
                                resultPrint.text = result.toString()
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

    private fun addSubscription(disposable: Disposable?) {
        disposable?.let { compositeDisposable.add(disposable) }
    }

    private fun getData(error: Exception?, result: GetContentByExternalIdQuery.GetContentByExternalId?): Observable<GetContentByExternalIdQuery.GetContentByExternalId>? {
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