package com.realifetech.sdk.sell.weboredering

import android.content.Context
import android.content.Intent
import javax.inject.Inject

class WebOrderingFeature @Inject constructor(private val context: Context) {

    fun startActivity() {
        val intent = Intent(context, WebOrderingActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}