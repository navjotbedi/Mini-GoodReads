package com.dehaat.goodreads.manager

import android.content.Context
import com.dehaat.goodreads.db.CoreDatabase
import com.dehaat.goodreads.db.dao.AuthorDao
import com.dehaat.goodreads.db.dao.BookDao

class DBManager(context: Context) {

    private val bookDao: BookDao
    private val authorDao: AuthorDao

    init {
        val coreDatabase = CoreDatabase.getInstance(context)
        bookDao = coreDatabase.bookDao()
        authorDao = coreDatabase.authorDao()
    }

}