package com.dehaat.goodreads.core

import android.app.Application
import android.util.Log
import com.dehaat.goodreads.utils.GlobalConfig
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.plugins.RxJavaPlugins

@HiltAndroidApp
class MiniGoodReads : Application() {

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { error: Throwable? ->
            Log.e(
                GlobalConfig.Core.RELEASE_LOG_TAG,
                Log.getStackTraceString(error)
            )
        }
    }

}