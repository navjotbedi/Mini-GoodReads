package com.dehaat.goodreads.manager

import android.content.Context
import androidx.preference.PreferenceManager
import com.dehaat.goodreads.utils.GlobalConfig.Preference.KEY_AUTH_TOKEN
import javax.inject.Inject

class PreferenceManager @Inject constructor(context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var authToken: String?
        get() = sharedPreferences.getString(KEY_AUTH_TOKEN, null)
        set(value) = sharedPreferences.edit().putString(KEY_AUTH_TOKEN, value).apply()

    val isLoggedIn: Boolean
        get() = (authToken != null)
}