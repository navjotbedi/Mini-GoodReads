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
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.dehaat.goodreads.R
import com.dehaat.goodreads.adapters.AuthorAdapter
import com.dehaat.goodreads.manager.PreferenceManager
import com.dehaat.goodreads.utils.GlobalConfig.Extras.URL_CODE
import com.dehaat.goodreads.utils.GlobalConfig.Extras.URL_DEVELOPER
import com.dehaat.goodreads.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Activity to hold login Author and Book fragment
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), AuthorAdapter.OnClickListener {

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var utils: Utils

    override fun onAuthorClicked(authorId: Long) {
        supportFragmentManager.commit {
            add(R.id.frameLayout, BookFragment::class.java, null).addToBackStack(null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutMenu -> {
                preferenceManager.authToken = null
                val intent = Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                true
            }
            R.id.developerMenu -> {
                utils.openUrl(URL_DEVELOPER)
                true
            }
            R.id.viewCodeMenu -> {
                utils.openUrl(URL_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}