package com.dehaat.goodreads.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.manager.DBManager
import com.dehaat.goodreads.utils.Utils
import io.reactivex.rxjava3.core.Single

class BookListViewModel @ViewModelInject constructor(private val dbManager: DBManager,
                                                     private val utils: Utils) : ViewModel() {

    fun books(authorId: Long): Single<List<BookViewModel>> = dbManager.getBooks(authorId)
        .flatMap { getBookViewModels(it) }

    private fun getBookViewModels(bookList: List<Book>): Single<List<BookViewModel>> {
        return Single.create<List<BookViewModel>> {
            val bookViewModels = mutableListOf<BookViewModel>()
            for (book in bookList) {
                bookViewModels.add(BookViewModel(book, utils))
            }
            it.onSuccess(bookViewModels)
        }
    }
}