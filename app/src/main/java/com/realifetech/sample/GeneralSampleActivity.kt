package com.realifetech.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.domain.Result
import kotlinx.android.synthetic.main.activity_general_sample.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeneralSampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_sample)

        val storage = DeviceConfigurationStorage(this)
        clientSecretEditText.setText(storage.clientSecret)

        clientSecretEditText.doOnTextChanged { text, _, _, _ ->
            storage.clientSecret = text.toString()
            RealifeTech.getGeneral().configuration.clientSecret = text.toString()
        }

        registerDeviceButton.setOnClickListener {
            registerDeviceSdk()
        }
    }

    private fun registerDeviceSdk() {
        progressBar.isVisible = true
        resultTextView.text = ""
        operationTextView.text = "Registering device"

        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                RealifeTech.getGeneral().registerDevice()
            }

            deviceIdentifierTextView.text  = RealifeTech.getGeneral().deviceIdentifier

            progressBar.isVisible = false
            resultTextView.text = when (result) {
                is Result.Success -> "Success!"
                is Result.Error -> result.exception.message ?: "Unknown error"
            }
        }

    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, GeneralSampleActivity::class.java))
        }
    }
}