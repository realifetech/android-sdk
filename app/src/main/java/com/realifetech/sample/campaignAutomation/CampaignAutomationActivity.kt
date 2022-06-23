package com.realifetech.sample.campaignAutomation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.realifetech.sample.R
import com.realifetech.sample.utils.SampleAppCompatActivity
import com.realifetech.sample.utils.loadImage
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.campaignautomation.data.model.*
import com.realifetech.type.ContentType

import kotlinx.android.synthetic.main.activity_campaign_automation_sample.*
import kotlinx.android.synthetic.main.banner_view.view.*


class CampaignAutomationActivity : SampleAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_automation_sample)
        val layout = findViewById<LinearLayout>(R.id.campaignAutomationLayout)
        val location = findViewById<EditText>(R.id.location)
        val button = findViewById<Button>(R.id.button)

        val factories =
            mutableMapOf<ContentType, RLTCreatableFactory<*>>(ContentType.BANNER to IntegratorBannerFactory())

        RealifeTech.getCampaignAutomation().factories(factories)

        button.setOnClickListener {
            layout.removeAllViews()
            progressBar.isVisible = true
            RealifeTech.getCampaignAutomation().apply {
                fetch(
                    location.text.toString()
                ) { error, response ->
                    progressBar.isVisible = false
                    response.forEachIndexed { _, item ->
                        item?.let {
                            layout.addView(item)
                        }
                    }
                    error?.let {
                        Toast.makeText(
                            this@CampaignAutomationActivity,
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        }

    }

    // This is the Fabric given by the Integrator
    inner class IntegratorBannerFactory : RLTBannerFactory {

        override fun create(dataModel: RLTDataModel?): RLTViewCreatable {
            return IntegratorBanner(
                context = this@CampaignAutomationActivity,
                bannerDataModel = dataModel as BannerDataModel
            )
        }
    }

    // This is the UI given by the Integrator
    @SuppressLint("ViewConstructor")
    class IntegratorBanner(
        context: Context,
        bannerDataModel: BannerDataModel
    ) : ConstraintLayout(context), RLTViewCreatable {

        init {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.banner_view, this, false)
            val set = ConstraintSet()
            addView(view)
            set.clone(this)
            set.match(view, this)
            view.title.text = bannerDataModel.title
            view.subtitle.text = bannerDataModel.subtitle
            view.subtitle.isVisible = !bannerDataModel.subtitle.isNullOrEmpty()
            view.title.isVisible = !bannerDataModel.title.isNullOrEmpty()
            view.bannerImage.loadImage(context, bannerDataModel.imageUrl)
            bannerDataModel.generateLinkHandler()?.let { url ->
                view.setOnClickListener {
                    val uriUrl = Uri.parse(url)
                    val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                    context.startActivity(launchBrowser)
                }
            }
        }

        private fun ConstraintSet.match(view: View, parentView: View) {
            this.connect(view.id, ConstraintSet.TOP, parentView.id, ConstraintSet.TOP)
            this.connect(view.id, ConstraintSet.START, parentView.id, ConstraintSet.START)
            this.connect(view.id, ConstraintSet.END, parentView.id, ConstraintSet.END)
            this.connect(view.id, ConstraintSet.BOTTOM, parentView.id, ConstraintSet.BOTTOM)
        }

    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CampaignAutomationActivity::class.java))
        }
    }
}