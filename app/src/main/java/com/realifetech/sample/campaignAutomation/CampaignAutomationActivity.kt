package com.realifetech.sample.campaignAutomation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.realifetech.sample.R
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.campaignautomation.data.model.*
import com.realifetechCa.type.ContentType
import kotlinx.android.synthetic.main.banner_view.view.*


class CampaignAutomationActivity : AppCompatActivity() {

    @SuppressLint("CheckResult", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_automation_sample)

        val layout = findViewById<ConstraintLayout>(R.id.campaignAutomationLayout)
        val set = ConstraintSet()
        set.clone(layout)
        val factories =
            mutableMapOf<ContentType, RLTCreatableFactory<*>>(ContentType.BANNER to IntegratorBannerFactory())
        RealifeTech.getCampaignAutomation().apply {
            fetch(
                "homepage-top-view",
                factories = factories
            ) { error, response ->
                response.let {

                    val bannerView = response[0] as View
                    layout.addView(bannerView)
                    set.applyTo(layout)

                }
                error?.let {

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
    class IntegratorBanner(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        bannerDataModel: BannerDataModel
    ) : ConstraintLayout(context, attrs, defStyleAttr), RLTViewCreatable {

        init {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.banner_view, this, false)
            val set = ConstraintSet()
            addView(view)
            set.clone(this)
            set.match(view, this)
            view.title.text = bannerDataModel.title
            view.subtitle.text = bannerDataModel.subtitle
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