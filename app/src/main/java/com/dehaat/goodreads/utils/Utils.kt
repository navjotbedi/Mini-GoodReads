package com.dehaat.goodreads.utils

import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class Utils @Inject constructor(@ApplicationContext val context: Context) {

    fun isDualMode(): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    fun getDateFormatter(): SimpleDateFormat {
        return SimpleDateFormat("dd-mm-yyyy", Locale.US)
    }

}