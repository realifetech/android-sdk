package com.realifetech.sample.widgets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.realifetech.sample.R
import com.realifetech.sdk.RealifeTech
import com.realifetech.type.ScreenType
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_widgets_sample.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WidgetsSampleActivity : AppCompatActivity() {

    private var selectedType: ScreenType? = null
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgets_sample)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectedType = when (checkedId) {
                R.id.radioBooking -> ScreenType.booking
                R.id.radioDiscovery -> ScreenType.discover
                R.id.radioShop -> ScreenType.shop
                R.id.radioEvents -> ScreenType.events
                R.id.radioWallet -> ScreenType.wallet
                R.id.radioLineup -> ScreenType.lineup
                R.id.radioGeneric -> ScreenType.generic
                else -> null
            }
        }

        GlobalScope.launch(Dispatchers.Main) {


            queryWidgets.setOnClickListener {
                if (screenTypeSelected()) {
                    queryWidgets()
                }

            }
            queryScreenTitle.setOnClickListener {
                if (screenTypeSelected()) {
                    queryScreenTitle()
                }
            }
        }

    }

    private fun screenTypeSelected(): Boolean {
        if (selectedType == null) {
            Toast.makeText(this, "Please select a type", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun queryWidgets() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = RealifeTech.getContent().getWidgetsByScreenType(selectedType!!, 10, 1)
            withContext(Dispatchers.Main) {
                result.fold(
                    onSuccess = { response ->
                        displayToast("Next Page: ${response.nextPage}\nWidget loading: ${response.items}")
                    },
                    onFailure = { error ->
                        displayToast("Error loading: ${error.message}")
                    }
                )
            }
        }
    }

    private fun queryScreenTitle() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = RealifeTech.getContent().getScreenTitleByScreenType(selectedType!!)
            withContext(Dispatchers.Main) {
                displayToast("Screen Title translations loading: $result")
            }
        }
    }


    private fun displayToast(message: String?) {
        Toast.makeText(
            this@WidgetsSampleActivity,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, WidgetsSampleActivity::class.java))
        }
    }
}
