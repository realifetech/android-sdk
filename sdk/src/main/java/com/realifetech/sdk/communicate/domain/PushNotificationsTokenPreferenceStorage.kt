package com.realifetech.sdk.communicate.domain

import android.content.Context
import androidx.core.content.edit
import com.realifetech.sdk.core.data.database.preferences.AbstractPreferences
import javax.inject.Inject

class PushNotificationsTokenPreferenceStorage @Inject constructor(context: Context) :
    AbstractPreferences(context),
    PushNotificationStorage {

    override var token: String
        get() = preferences.getString(TOKEN_KEY, "").orEmpty()
        set(value) {
            preferences.edit { putString(TOKEN_KEY, value) }
        }

    override fun removeToken() {
        preferences.edit { remove(TOKEN_KEY) }
    }

    companion object {
        private const val TOKEN_KEY = "PushNotificationsToken"
    }
}