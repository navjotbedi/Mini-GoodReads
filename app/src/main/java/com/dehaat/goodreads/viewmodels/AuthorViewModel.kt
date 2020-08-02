package com.dehaat.goodreads.viewmodels

import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.db.entity.Author

class AuthorViewModel constructor(author: Author) : ViewModel() {
    val id = author.id
    val name = author.name
    val bio = author.bio
}