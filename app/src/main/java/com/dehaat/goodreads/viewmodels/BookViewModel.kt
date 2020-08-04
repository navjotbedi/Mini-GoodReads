package com.dehaat.goodreads.viewmodels

import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.utils.Utils

class BookViewModel(var book: Book, val utils: Utils) : ItemViewModel() {

    val price: String
        get() = "${book.price} INR"
    val description: String
        get() = book.description
    val publishedDate: String
        get() = book.publishedDate?.let { utils.getDateFormatter().format(it) } ?: ""
    val publisherName: String
        get() = "Publisher: ${book.publisherName}"
    val title: String
        get() = book.title

}