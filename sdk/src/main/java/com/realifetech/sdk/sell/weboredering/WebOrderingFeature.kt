package com.realifetech.sdk.sell.weboredering

import android.content.Intent
import com.realifetech.core_sdk.domain.CoreConfiguration.context

object WebOrderingFeature {

    fun startActivity() {
        val intent = Intent(context, WebOrderingActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}