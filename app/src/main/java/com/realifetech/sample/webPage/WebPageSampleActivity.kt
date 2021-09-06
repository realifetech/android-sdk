package com.realifetech.sample.webPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.realifetech.fragment.FragmentWebPage
import com.realifetech.sample.R
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.RealifeTech
import com.realifetech.type.WebPageType
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_communication_sample.*
import kotlinx.android.synthetic.main.activity_web_page_sample.*

class WebPageSampleActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_page_sample)

        var selectedType: WebPageType? = null
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedType = when (checkedId) {
                R.id.audioGuidesHelp -> WebPageType.AUDIOGUIDESHELP
                R.id.tAndC -> WebPageType.TANDC
                R.id.privacy -> WebPageType.PRIVACY
                R.id.about -> WebPageType.ABOUT
                R.id.aboutCompany -> WebPageType.ABOUTCOMPANY
                else -> WebPageType.UNKNOWN__
            }
        }

        queryWebPage.setOnClickListener {
            selectedType?.let { webPage ->
                Single.just(RealifeTech.getContent().getWebPage(webPage) { error, webPage ->
                    addSubscription(getData(error, webPage)?.subscribe(
                        {
                            result.text = webPage?.url
                        }, {
                            Toast.makeText(
                                this@WebPageSampleActivity,
                                "${this@WebPageSampleActivity.getString(R.string.error_in_loading)}${error?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        })
                    )
                })


            } ?: run {
                Toast.makeText(this, this.getString(R.string.please_select_type), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
        }
    }

    private fun addSubscription(disposable: Disposable?) {
        disposable?.let { compositeDisposable.add(disposable) }
    }

    private fun getData(error: Exception?, result: FragmentWebPage?): Observable<FragmentWebPage>? {
        return error?.let { exception ->
            Observable.error(exception)
        } ?: result?.run {
            Observable.just(this)
        }?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, WebPageSampleActivity::class.java))
        }
    }
}