package com.dehaat.goodreads.screens

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.dehaat.goodreads.R
import com.dehaat.goodreads.adapters.AuthorAdapter
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_AUTHOR_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), AuthorAdapter.OnClickListener {

    override fun onAuthorClicked(authorId: Long) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            (supportFragmentManager.findFragmentById(R.id.bookFragment) as BookFragment).subscribeUi(authorId)
        } else {
            val bundle = Bundle(1).apply { putLong(COLUMN_AUTHOR_ID, authorId) }
            supportFragmentManager.commit { add(R.id.frameLayout, BookFragment::class.java, bundle).addToBackStack(null) }
        }
    }
}