package com.dehaat.goodreads.viewmodels

import androidx.lifecycle.ViewModel
import java.util.*

class BookViewModel : ViewModel() {

    private var title: String? = null
    private var publisher: String? = null
    private var publishedDate: Date? = null
    private var description: String? = null
    private var price: Int? = 0

}