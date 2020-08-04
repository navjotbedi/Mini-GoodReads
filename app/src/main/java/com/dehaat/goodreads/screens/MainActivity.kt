package com.dehaat.goodreads.screens

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.dehaat.goodreads.R
import com.dehaat.goodreads.adapters.AuthorAdapter
import com.dehaat.goodreads.manager.PreferenceManager
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_AUTHOR_ID
import com.dehaat.goodreads.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), AuthorAdapter.OnClickListener {

    @Inject lateinit var preferenceManager: PreferenceManager
    @Inject lateinit var utils: Utils

    override fun onAuthorClicked(authorId: Long) {
        if (utils.isDualMode()) {
            (supportFragmentManager.findFragmentById(R.id.bookFragment) as BookFragment).subscribeUi(authorId)
        } else {
            val bundle = Bundle(1).apply { putLong(COLUMN_AUTHOR_ID, authorId) }
            supportFragmentManager.commit {
                add(R.id.frameLayout, BookFragment::class.java, bundle).addToBackStack(null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.logoutMenu) {
            preferenceManager.authToken = null
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            true
        } else super.onOptionsItemSelected(item)
    }
}