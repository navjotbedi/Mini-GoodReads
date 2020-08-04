package com.dehaat.goodreads.viewmodels

import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.utils.Utils

class BookViewModel(var book: Book, val utils: Utils? = null) : ViewModel() {

    val price: String
        get() = "${book.price} INR"
    val description: String
        get() = book.description
    val publishedDate: String
        get() = "10 July" //book.publishedDate?.let { utils.getDateFormatter().format(it) }
    val publisherName: String
        get() = "Publisher: ${book.publisherName}"
    val title: String
        get() = book.title

}