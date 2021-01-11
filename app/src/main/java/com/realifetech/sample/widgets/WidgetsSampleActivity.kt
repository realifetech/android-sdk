package com.realifetech.sample.widgets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.widgets.di.WidgetsModuleProvider
import com.realifetech.sample.R
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sample.utils.disposedBy
import com.realifetech.sdk.RealifeTech
import com.realifetech.type.ScreenType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_widgets_sample.*
import kotlinx.coroutines.*

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

        queryWidgets.setOnClickListener {
            if (selectedType == null) {
                Toast.makeText(this, "Please select a type", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val storage = DeviceConfigurationStorage(this)

            GlobalScope.launch(Dispatchers.Main) {

                val widgetsRepo = WidgetsModuleProvider.provideWidgetsRepository(
                    storage.graphQl,
                    withContext(Dispatchers.IO) { RealifeTech.getGeneral().deviceIdentifier }
                )
                widgetsRepo.getWidgetsByScreenTypeFlowable(selectedType!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).flatMapIterable {
                        when (it) {
                            is Result.Success -> {
                                it.data
                            }
                            is Result.Error -> {
                                throw  it.exception
                            }
                        }
                    }.subscribeBy(
                        {
                            Toast.makeText(
                                this@WidgetsSampleActivity,
                                "Error loading: ${it.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }, {
                            Log.d("Completion", "Completed")

                        }, {
                            Toast.makeText(
                                this@WidgetsSampleActivity,
                                "Widget loading: $it",
                                Toast.LENGTH_LONG
                            ).show()
                        }).disposedBy(compositeDisposable)
            }

// In case we have coroutines instead of RxJava, you can do the following

//            GlobalScope.launch(Dispatchers.IO) {
//                CoreConfiguration.deviceId = General.instance.deviceIdentifier
//                val widgetsRepo = WidgetsModuleProvider.provideWidgetsRepository(storage.graphQl)
//                val widgetsResult = widgetsRepo.getWidgetsByScreenType(selectedType!!)
//                when (widgetsResult) {
//                    is Result.Success -> {
//                        widgetsResult.data.forEach {
//                            Log.d("Result", it.toString())
//                        }
//
//                    }
//                    is Result.Error -> {
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(
//                                this@WidgetsSampleActivity,
//                                "Error in loading: ${widgetsResult.exception.message}",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    }
//                }
//            }
        }
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
