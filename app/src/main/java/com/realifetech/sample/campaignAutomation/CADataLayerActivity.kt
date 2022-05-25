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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.compose.rememberImagePainter
import com.realifetech.sample.R
import com.realifetech.sample.utils.loadImage
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.campaignautomation.data.model.BannerDataModel
import com.realifetech.sdk.campaignautomation.data.model.RLTItem
import com.realifetech.sdk.campaignautomation.data.model.RLTViewCreatable

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
    fun BannerListView(response: List<RLTItem?>) {
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

    private fun addingView(layout: LinearLayout) {


        RealifeTech.getCampaignAutomation().apply {
            fetchRLTDataModels(
                location.text.toString()
            ) { error, response ->
                progressBar.isVisible = false
                response.forEachIndexed { _, item ->
                    item?.let {
                        val banner = IntegratorBanner(
                            this@CADataLayerActivity,
                            it.data as BannerDataModel
                        )
                        layout.addView(banner)
                    }
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

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CADataLayerActivity::class.java))
        }
    }
}