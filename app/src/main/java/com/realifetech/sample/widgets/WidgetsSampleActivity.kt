package com.realifetech.sample.widgets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.realifetech.sample.R
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.sdk.core.utils.Result
import com.realifetech.type.ScreenType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
                R.id.radioBooking -> ScreenType.BOOKING
                R.id.radioDiscovery -> ScreenType.DISCOVER
                R.id.radioShop -> ScreenType.SHOP
                R.id.radioEvents -> ScreenType.EVENTS
                R.id.radioWallet -> ScreenType.WALLET
                R.id.radioLineup -> ScreenType.LINEUP
                R.id.radioGeneric -> ScreenType.GENERIC
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
        GlobalScope.launch(Dispatchers.IO) {
            RealifeTech.getContent()
                .getWidgetsByScreenType(selectedType!!, 10, 1) { error, response ->
                    GlobalScope.launch(Dispatchers.IO) {
                        withContext(Dispatchers.Main) {
                            error?.let {
                                displayToast("Error loading:${it.message}")
                            }
                            response?.let {
                                displayToast(
                                    "Next Page: ${it.nextPage} " + "\n Widget loading: ${it.items} "
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun queryScreenTitle() {
        GlobalScope.launch(Dispatchers.IO) {
            RealifeTech.getContent().getScreenTitleByScreenType(selectedType!!) { error, response ->
                GlobalScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        error?.let {
                            displayToast("Error loading:${it.message}")
                        }
                        response?.let {
                            displayToast("Screen Title translations loading: $it")
                        }
                    }
                }
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
