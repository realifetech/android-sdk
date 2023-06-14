package com.realifetech.sample.webPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.realifetech.fragment.FragmentWebPage
import com.realifetech.sample.R
import com.realifetech.sdk.RealifeTech
import com.realifetech.type.WebPageType
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_web_page_sample.*
import kotlinx.coroutines.launch

class WebPageSampleActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_page_sample)

        var selectedType: WebPageType? = null
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedType = when (checkedId) {
                R.id.audioGuidesHelp -> WebPageType.audioGuidesHelp
                R.id.tAndC -> WebPageType.tAndC
                R.id.privacy -> WebPageType.privacy
                R.id.about -> WebPageType.about
                R.id.aboutCompany -> WebPageType.aboutCompany
                R.id.purchasesHelp -> WebPageType.purchasesHelp
                else -> WebPageType.UNKNOWN__
            }
        }
        queryWebPage.setOnClickListener {
            selectedType?.let { webPage ->
                lifecycleScope.launch {
                    val result = RealifeTech.getContent().getWebPage(webPage)
                }
            } ?: run {
                Toast.makeText(this@WebPageSampleActivity, getString(R.string.please_select_type), Toast.LENGTH_LONG).show()
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