package com.dehaat.goodreads.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.manager.DBManager
import com.dehaat.goodreads.network.RestApi
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class AuthorListViewModel @ViewModelInject constructor(dbManager: DBManager, restApi: RestApi) : ViewModel() {

    val authors: Flowable<List<AuthorViewModel>> = dbManager.getAuthors()
        .flatMap { getAuthorViewModels(it) }
        .mergeWith(
            restApi.getBooks()
                .flatMap { dbManager.fillDb(it) }
                .flatMap { dbManager.getAuthors() }
                .flatMap { getAuthorViewModels(it) }
        )

    private fun getAuthorViewModels(authorList: List<Author>): Single<List<AuthorViewModel>> {
        return Single.create<List<AuthorViewModel>> {
            val authorViewModels = mutableListOf<AuthorViewModel>()
            for (author in authorList) {
                authorViewModels.add(AuthorViewModel((author)))
            }
            it.onSuccess(authorViewModels)
        }
    }

}