package com.realifetech.sample.webPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.realifetech.sample.R
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.RealifeTech
import com.realifetech.type.WebPageType
import kotlinx.android.synthetic.main.activity_web_page_sample.*

class WebPageSampleActivity : AppCompatActivity() {

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
                val storage = DeviceConfigurationStorage(this)
                RealifeTech.getContent().getWebPage(webPage) { error, webPage ->
                    webPage?.let {
                        result.text = webPage.url
                    }
                    error?.let {
                        Toast.makeText(
                            this@WebPageSampleActivity,
                            "${this@WebPageSampleActivity.getString(R.string.error_in_loading)}${error.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } ?: run {
                Toast.makeText(this, this.getString(R.string.please_select_type), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, WebPageSampleActivity::class.java))
        }
    }
}