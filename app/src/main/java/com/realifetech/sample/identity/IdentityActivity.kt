package com.realifetech.sample.identity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.realifetech.sample.R
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sample.databinding.ActivityIdentityBinding
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage

class IdentityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentityBinding

    private lateinit var storage: DeviceConfigurationStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage = DeviceConfigurationStorage(this)

        setupView()
    }

    private fun setupView() {
        binding.apply {
            identityTitle.text =
                if (storage.isLoggedIn) getString(R.string.identity_logout_title)
                else getString(R.string.identity_login_title)
            emailEditTextView.isVisible = !storage.isLoggedIn
            passwordInputLayout.isVisible = !storage.isLoggedIn
            identityLoginButton.isVisible = !storage.isLoggedIn
            identityLogoutButton.isVisible = storage.isLoggedIn
            setupLoginFlow()
            setupLogoutFlow()
        }
    }

    private fun setupLogoutFlow() {
        binding.identityLogoutButton.setOnClickListener {
            RealifeTech.getIdentity().logout()
            storage.isLoggedIn = false
            finish()
        }
    }

    private fun setupLoginFlow() {
        binding.apply {
            identityLoginButton.setOnClickListener {
                if (validInputs()) {
                    val preferences = AuthTokenStorage(this@IdentityActivity)
                    preferences.accessToken = userTokenEditTextView.text.toString()
                    RealifeTech.getIdentity().attemptLogin(
                        emailEditTextView.text.toString(),
                        FIRST_NAME, LAST_NAME,
                        storage.salt
                    ) { errorMessage ->
                        errorMessage?.let { error ->
                            error.message?.let {
                                Toast.makeText(
                                    this@IdentityActivity,
                                    it,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } ?: run {
                            storage.isLoggedIn = true
                            finish()
                        }


                    }
                }
            }
        }
    }

    private fun validInputs(): Boolean {
        binding.apply {
            if (emailEditTextView.text.isNullOrEmpty() ||
                !isEmailValid(emailEditTextView.text.toString())
            ) {
                emailEditTextView.error = resources.getString(R.string.identity_email)
                return false
            }
            if (passwordEditTextView.text.toString().isEmpty()) {
                passwordEditTextView.error = resources.getString(R.string.identity_password)
                return false
            }
            if (userTokenEditTextView.text.isNullOrEmpty()) {
                userTokenEditTextView.error =
                    resources.getString(R.string.user_access_token_message)
                return false
            }
            return true
        }
    }

    private fun isEmailValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    companion object {
        private const val FIRST_NAME = "user"
        private const val LAST_NAME = "test"
        fun start(context: Context) {
            context.startActivity(Intent(context, IdentityActivity::class.java))
        }
    }
}