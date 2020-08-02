package com.dehaat.goodreads.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.manager.DBManager
import io.reactivex.rxjava3.core.Single

class BookViewModel @ViewModelInject constructor(
    private val dbManager: DBManager
) : ViewModel() {
    fun books(authorId: Long): Single<List<Book>> = dbManager.getBooks(authorId)
}