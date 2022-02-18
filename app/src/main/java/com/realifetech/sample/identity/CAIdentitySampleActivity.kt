package com.realifetech.sample.identity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.realifetech.sample.R
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.identity.data.model.RLTAliasType
import com.realifetech.sdk.identity.data.model.RLTTraitType
import kotlinx.android.synthetic.main.activity_analytics_sample.*
import kotlinx.android.synthetic.main.activity_analytics_sample.actionEditText
import kotlinx.android.synthetic.main.activity_analytics_sample.newDictionaryCheckBox
import kotlinx.android.synthetic.main.activity_analytics_sample.oldDictionaryCheckBox
import kotlinx.android.synthetic.main.activity_analytics_sample.sendEventButton
import kotlinx.android.synthetic.main.activity_analytics_sample.typeEditText
import kotlinx.android.synthetic.main.activity_ca_identity.*
import kotlinx.android.synthetic.main.activity_communication_sample.operationTextView
import kotlinx.android.synthetic.main.activity_communication_sample.progressBar
import kotlinx.android.synthetic.main.activity_communication_sample.resultTextView

class CAIdentitySampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ca_identity)

        sendIdentifyButton.setOnClickListener {
            sendIdentify()
        }

        sendAliasButton.setOnClickListener {
            sendAlias()
        }

        clearIdButton.setOnClickListener {
            clearId()
        }


    }

    private fun clearId() {
        RealifeTech.getIdentity().clear()
    }

    private fun sendAlias() {
        progressBar.isVisible = true
        resultTextView.text = ""
        operationTextView.text = "Sending analytics event"

        RealifeTech.getIdentity().alias(
            aliasType = RLTAliasType.TdcAccountId,
            aliasId = aliasIdEditText.text.toString()
        ) { error, result ->
            progressBar.isVisible = false
            result.takeIf { it }?.let {
                resultTextView.text = "Success"
            }
            error?.let {
                resultTextView.text = "Failure"
            }

        }
    }

    private fun sendIdentify() {

        progressBar.isVisible = true
        resultTextView.text = ""
        operationTextView.text = "Sending analytics event"

        val map = mutableMapOf<RLTTraitType, Any>()
        map[RLTTraitType.LastName] = "lastName"
        map[RLTTraitType.Dynamic("dynamic value")] = 123

        RealifeTech.getIdentity().identify(
            userId = userIdEditText.text.toString(),
            map
        ) { error, result ->
            progressBar.isVisible = false
            result.takeIf { it }?.let {
                resultTextView.text = "Success"
            }
            error?.let {
                resultTextView.text = "Failure"
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CAIdentitySampleActivity::class.java))
        }
    }
}
