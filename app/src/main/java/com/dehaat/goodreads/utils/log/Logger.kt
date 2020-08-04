/*
 * Copyright (C) 2020 Navjot Singh Bedi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dehaat.goodreads.utils.log

import android.content.Context
import com.dehaat.goodreads.utils.GlobalConfig
import com.dehaat.goodreads.utils.GlobalConfig.Core.RELEASE_LOG_TAG
import timber.log.Timber
import javax.inject.Inject

class Logger @Inject constructor(private val context: Context) {

    init {
        if (Timber.treeCount() == 0) {
            if (GlobalConfig.Settings.ENABLE_LOGS) {
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(ReleaseTree())
            }
        }
    }

    fun v(any: Any, message: String) {
        Timber.v(composeMessage(any, message))
    }

    fun e(any: Any, message: String, release: Boolean = false) {
        if (release) Timber.tag(RELEASE_LOG_TAG).e(message)
        else Timber.e(composeMessage(any, message))
    }

    fun e(throwable: Throwable, release: Boolean = false) {
        if (release) Timber.tag(RELEASE_LOG_TAG).e(throwable)
        else Timber.e(throwable)
    }

    fun d(any: Any, message: String) {
        Timber.d(composeMessage(any, message))
    }

    fun i(any: Any, message: String, release: Boolean = false) {
        if (release) Timber.tag(RELEASE_LOG_TAG).i(message)
        else Timber.i(composeMessage(any, message))
    }

    private fun composeMessage(any: Any, message: String): String {
        return """${any.javaClass.simpleName}: $message"""
    }
}