package com.realifetech.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.model.shared.`object`.toBearerFormat
import kotlinx.android.synthetic.main.activity_identity_sample.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IdentitySampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identity_sample)
        val preferences = AuthTokenStorage(this)
        querySSOButton.setOnClickListener {
            if (!userBearerTextField.text.isNullOrBlank()) {
                preferences.accessToken = userBearerTextField.text.toString().toBearerFormat
                getUserSSO()
            } else {
                Snackbar.make(identityLayout, getString(R.string.user_access_token_message), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun getUserSSO() {
        progressBar.isVisible = true
        GlobalScope.launch(Dispatchers.IO) {
                RealifeTech.getIdentity().getSSO().getMyUserSSO { error, user ->
                 GlobalScope.launch(Dispatchers.IO) {
                     withContext(Dispatchers.Main) {
                         progressBar.isVisible = false
                         user?.let {
                             ssoId.text = user.id
                         }
                         error?.let {
                             ssoId.text = it.message
                         }
                 }
                 }
            }
        }
    }


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, IdentitySampleActivity::class.java))
        }
    }
}