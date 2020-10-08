package com.realifetech.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.Realifetech
import kotlinx.android.synthetic.main.activity_general_sample.*

class GeneralSampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_sample)

        val storage = DeviceConfigurationStorage(this)
        clientSecretEditText.setText(storage.clientSecret)

        clientSecretEditText.doOnTextChanged { text, _, _, _ ->
            storage.clientSecret = text.toString()
            Realifetech.getGeneral().configuration.clientSecret = text.toString()
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, GeneralSampleActivity::class.java))
        }
    }
}