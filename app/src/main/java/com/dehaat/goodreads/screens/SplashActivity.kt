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

package com.dehaat.goodreads.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.dehaat.goodreads.manager.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Activity to perform login
 */
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject lateinit var preferenceManager: PreferenceManager

    override fun onPostResume() {
        super.onPostResume()
        val intent = Intent(this, if (preferenceManager.isLoggedIn) MainActivity::class.java else LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}