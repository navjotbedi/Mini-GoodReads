package com.dehaat.goodreads.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.manager.DBManager
import com.dehaat.goodreads.network.RestApi
import io.reactivex.rxjava3.core.Single

class AuthorListViewModel @ViewModelInject constructor(dbManager: DBManager, restApi: RestApi) : ViewModel() {

    val authors: Single<List<Author>> = restApi.getBooks().flatMap { dbManager.fillDb(it) }.flatMap { dbManager.getAuthors() }

}