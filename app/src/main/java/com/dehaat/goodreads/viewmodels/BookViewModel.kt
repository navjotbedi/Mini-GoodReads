package com.dehaat.goodreads.viewmodels

import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.db.entity.Book

class BookViewModel constructor(book: Book) : ViewModel() {

    val title = book.title
    val publisher = book.publisherName
    val publishedDate = book.publishedDate.toString()
    val description = book.description
    val price = book.price

}