package com.realifetech.sample.identity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.realifetech.sample.R
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.identity.data.model.RLTAliasType
import com.realifetech.sdk.identity.data.model.RLTTraitType
import kotlinx.android.synthetic.main.activity_ca_identity.*
import kotlinx.android.synthetic.main.activity_communication_sample.operationTextView
import kotlinx.android.synthetic.main.activity_communication_sample.progressBar
import kotlinx.android.synthetic.main.activity_communication_sample.resultTextView
import kotlinx.coroutines.launch

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
        operationTextView.text = "Sending alias analytics event"

        val identity = RealifeTech.getIdentity()
        lifecycleScope.launch {
            try {
                identity.alias(
                    aliasType = RLTAliasType.TdcAccountId,
                    aliasId = aliasIdEditText.text.toString()
                )
                progressBar.isVisible = false
                resultTextView.text = "Success"
            } catch (e: Exception) {
                progressBar.isVisible = false
                resultTextView.text = "Failure"
                e.printStackTrace()
            }
        }
    }

    private fun sendIdentify() {
        progressBar.isVisible = true
        resultTextView.text = ""
        operationTextView.text = "Sending identify analytics event"

        val identity = RealifeTech.getIdentity()
        lifecycleScope.launch {
            try {
                val traits = mutableMapOf<RLTTraitType, Any>()
                traits[RLTTraitType.LastName] = "RLT Last Name"
                identity.identify(userIdEditText.text.toString(), traits)
                progressBar.isVisible = false
                resultTextView.text = "Success"
            } catch (e: Exception) {
                progressBar.isVisible = false
                resultTextView.text = "Failure"
                e.printStackTrace()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CAIdentitySampleActivity::class.java))
        }
    }
}
