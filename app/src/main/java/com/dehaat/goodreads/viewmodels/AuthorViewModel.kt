package com.dehaat.goodreads.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.manager.DBManager
import io.reactivex.rxjava3.core.Single

class AuthorViewModel @ViewModelInject constructor(
    dbManager: DBManager
) : ViewModel() {
    val authors: Single<List<Author>> = dbManager.getAuthors()
}