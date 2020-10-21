package com.realifetech.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.RealifeTech
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiUrlEditTextView.setText(RealifeTech.getGeneral().configuration.apiUrl)
        graphQlUrlEditTextView.setText(RealifeTech.getGeneral().configuration.graphApiUrl)

        val storage = DeviceConfigurationStorage(this)
        apiUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.getGeneral().configuration.apiUrl = text.toString()
            storage.apiUrl = text.toString()
        }
        graphQlUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.getGeneral().configuration.graphApiUrl = text.toString()
            storage.graphQl = text.toString()
        }

        generalButton.setOnClickListener { GeneralSampleActivity.start(this) }
        communicateButton.setOnClickListener { CommunicationSampleActivity.start(this) }
        analyticsButton.setOnClickListener { AnalyticsSampleActivity.start(this) }
    }
}
