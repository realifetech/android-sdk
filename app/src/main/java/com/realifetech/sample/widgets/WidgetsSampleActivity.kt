package com.realifetech.sample.widgets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.widgets.di.WidgetsModuleProvider
import com.realifetech.sample.R
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.type.ScreenType
import kotlinx.android.synthetic.main.activity_widgets_sample.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WidgetsSampleActivity : AppCompatActivity() {

    private var selectedType: ScreenType? = null

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

        queryWidgets.setOnClickListener {
            if (selectedType == null) {
                Toast.makeText(this, "Please select a type", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val storage = DeviceConfigurationStorage(this)
            GlobalScope.launch(Dispatchers.IO) {
                val widgetsRepo = WidgetsModuleProvider.provideWidgetsRepository(storage.graphQl)
                val widgetsResult = widgetsRepo.getWidgetsByScreenType(selectedType!!)
                when (widgetsResult) {
                    is Result.Success -> {

                    }
                    is Result.Error -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@WidgetsSampleActivity,
                                "Error in loading: ${widgetsResult.exception.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, WidgetsSampleActivity::class.java))
        }
    }
}
