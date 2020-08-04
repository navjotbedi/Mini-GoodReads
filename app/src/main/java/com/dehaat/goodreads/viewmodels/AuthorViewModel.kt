package com.dehaat.goodreads.viewmodels

import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.db.entity.Author

class AuthorViewModel(var author: Author) : ViewModel() {
    var isSelected: Boolean = false
}