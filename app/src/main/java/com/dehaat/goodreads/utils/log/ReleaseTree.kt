package com.dehaat.goodreads.utils.log

import android.util.Log
import com.dehaat.goodreads.utils.GlobalConfig
import timber.log.Timber

internal class ReleaseTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (tag == GlobalConfig.Core.RELEASE_LOG_TAG) Log.println(priority, tag, message)
    }

}