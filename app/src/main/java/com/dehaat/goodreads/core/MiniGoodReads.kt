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
            Log.e(GlobalConfig.Core.RELEASE_LOG_TAG, Log.getStackTraceString(error))
        }
    }

}