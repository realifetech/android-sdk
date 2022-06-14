package com.realifetech.sample.campaignAutomation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.compose.rememberImagePainter
import com.realifetech.sample.R
import com.realifetech.sample.utils.loadImage
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.campaignautomation.data.model.*
import com.realifetech.sdk.campaignautomation.data.utils.RLTContentConverter
import com.realifetechCa.type.ContentType
import kotlinx.android.synthetic.main.activity_campaign_automation_datal_layer_sample.*
import kotlinx.android.synthetic.main.banner_view.view.*


class CADataLayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_automation_datal_layer_sample)

        val layout = findViewById<LinearLayout>(R.id.caAddLayout)
        var uiType: String?

        button.setOnClickListener {

            layout.removeAllViews()
            recyclerview.visibility = View.GONE
            composeView.visibility = View.GONE

            progressBar.isVisible = true
            val selectedId = radioGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(selectedId)

            uiType = radioButton.text.toString()
            when {
                uiType.equals(resources.getString(R.string.ca_integrator_banner)) -> {
                    addingView(layout)
                }
                uiType.equals(resources.getString(R.string.ca_jetpack_compose)) -> {
                    usingJetpackCompose()
                }
                uiType.equals(resources.getString(R.string.ca_recycler_view)) -> {
                    usingRecyclerView()
                }
            }

        }
    }


    // Example of integration with RLTCreatableFactories and RLTContentConverter
    // given the response item list it is the passed to factories and the converter
    // to return a list of view that can be easily added
    private fun addingView(layout: LinearLayout) {
        RealifeTech.getCampaignAutomation().apply {
            fetchRLTDataModels(
                location.text.toString()
            ) { error, response ->
                progressBar.isVisible = false
                val factories =
                    mutableMapOf<ContentType, RLTCreatableFactory<*>>(ContentType.BANNER to IntegratorBannerFactory())
                val items = RLTContentConverter().convert(response, factories)
                items.forEach {
                    layout.addView(it)
                }
                error?.let {
                    Toast.makeText(
                        this@CADataLayerActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    // Example of integration with recycler view to inflate the data
    private fun usingRecyclerView() {

        recyclerview.visibility = View.VISIBLE

        RealifeTech.getCampaignAutomation().apply {
            fetchRLTDataModels(
                location.text.toString()
            ) { error, response ->
                progressBar.isVisible = false
                recyclerview.layoutManager = LinearLayoutManager(this@CADataLayerActivity)
                val adapter = CampaignAdapter(response)
                recyclerview.adapter = adapter
                error?.let {
                    Toast.makeText(
                        this@CADataLayerActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    // Example of factory created by the integrator that return a list of views
    inner class IntegratorBannerFactory : RLTBannerFactory {
        override fun create(dataModel: RLTDataModel?): RLTViewCreatable {
            return IntegratorBanner(
                context = this@CADataLayerActivity,
                bannerDataModel = dataModel as BannerDataModel
            )
        }
    }


    // Example of UI provided by the Integrator
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

            view.setOnClickListener {
                val uriUrl = Uri.parse(bannerDataModel.imageUrl)
                val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                context.startActivity(launchBrowser)
            }

        }

        private fun ConstraintSet.match(view: View, parentView: View) {
            this.connect(view.id, ConstraintSet.TOP, parentView.id, ConstraintSet.TOP)
            this.connect(view.id, ConstraintSet.START, parentView.id, ConstraintSet.START)
            this.connect(view.id, ConstraintSet.END, parentView.id, ConstraintSet.END)
            this.connect(view.id, ConstraintSet.BOTTOM, parentView.id, ConstraintSet.BOTTOM)
        }

    }

    // Example of inflating the view via Jetpack Compose
    private fun usingJetpackCompose() {

        composeView.visibility = View.VISIBLE

        RealifeTech.getCampaignAutomation().apply {
            fetchRLTDataModels(
                location.text.toString()
            ) { error, response ->
                progressBar.isVisible = false
                composeView.setContent {
                    BannerListView(response)
                }
                error?.let {
                    Toast.makeText(
                        this@CADataLayerActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    @Composable
    fun BannerListView(response: List<RLTContentItem?>) {
        LazyColumn() {
            items(items = response) {
                BannerView(it?.data as BannerDataModel?)
            }
        }
    }

    @Composable
    fun BannerView(bannerDataModel: BannerDataModel?) {
        Column() {
            Image(
                painter = rememberImagePainter(bannerDataModel?.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(340.dp)
            )
            bannerDataModel?.title?.let { Text(text = it) }
            bannerDataModel?.subtitle?.let { Text(text = it) }

        }
    }


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CADataLayerActivity::class.java))
        }
    }
}