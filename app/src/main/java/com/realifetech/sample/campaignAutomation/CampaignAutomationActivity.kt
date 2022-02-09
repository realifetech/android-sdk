package com.realifetech.sample.campaignAutomation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.realifetech.sample.R
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.campaignautomation.data.model.*
import com.realifetechCa.type.ContentType
import kotlinx.android.synthetic.main.banner_view.view.*


class CampaignAutomationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_automation_sample)

        val layout = findViewById<LinearLayout>(R.id.campaignAutomationLayout)

        val factories =
            mutableMapOf<ContentType, RLTCreatableFactory<*>>(ContentType.BANNER to IntegratorBannerFactory())

        val location = findViewById<EditText>(R.id.location)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            RealifeTech.getCampaignAutomation().apply {
                fetch(
                    location.text.toString(),
                    factories = factories
                ) { error, response ->
                    response.forEachIndexed { _, banner ->
                        banner?.let {
                            layout.addView(banner)
                        }
                    }
                    error?.let {

                    }
                }
            }

        }

    }

    // This is the Fabric given by the Integrator
    inner class IntegratorBannerFactory : RLTBannerFactory {
        override fun create(dataModel: BannerDataModel): RLTViewCreatable {
            return IntegratorBanner(
                context = this@CampaignAutomationActivity,
                bannerDataModel = dataModel
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
            view.url.text = bannerDataModel.url
            view.imageUrl.text = bannerDataModel.imageUrl
            view.language.text = bannerDataModel.language
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