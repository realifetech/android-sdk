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
import com.realifetech.sdk.campaignautomation.data.model.BannerDataModel
import com.realifetech.sdk.campaignautomation.data.model.RLTCreatable
import kotlinx.android.synthetic.main.banner_view.view.*


class CampaignAutomationActivity : AppCompatActivity() {

    @SuppressLint("CheckResult", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_automation_sample)

        val layout = findViewById<ConstraintLayout>(R.id.campaignAutomationLayout)
        val set = ConstraintSet()
        set.clone(layout)
        RealifeTech.getCampaignAutomation().fetch("homepage-top-view") { error, response ->
            response?.let {


                val bannerView =
                    IntegratorBanner(
                        this,
                        bannerDataModel = response[0] as BannerDataModel
                    )

                layout.addView(bannerView)
                set.applyTo(layout)


            }
            error?.let {

            }
        }

    }


    class IntegratorBanner(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        bannerDataModel: BannerDataModel
    ) : ConstraintLayout(context, attrs, defStyleAttr), RLTCreatable {

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