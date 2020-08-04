/*
 * Copyright (C) 2020 Navjot Singh Bedi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dehaat.goodreads.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.manager.DBManager
import com.dehaat.goodreads.network.RestApi
import com.dehaat.goodreads.utils.Utils
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class MainViewModel @ViewModelInject constructor(private val dbManager: DBManager,
                                                 private val restApi: RestApi,
                                                 private val utils: Utils) : ViewModel() {

    val authorSelected = MutableLiveData<Long>()
    var checkedPosition = -1
    var authorViewModels: List<AuthorViewModel>? = null
    var bookViewModels: List<BookViewModel>? = null

    fun select(authorId: Long) {
        authorSelected.value = authorId
    }

    fun getAuthors(): Flowable<List<AuthorViewModel>> {
        return dbManager.getAuthors()
            .flatMap { getAuthorViewModels(it) }
            .mergeWith(
                restApi.getBooks()
                    .flatMap { dbManager.fillDb(it) }
                    .flatMap { dbManager.getAuthors() }
                    .flatMap { getAuthorViewModels(it) }
            )
    }

    fun getBooks(authorId: Long): Single<List<BookViewModel>> {
        return dbManager
            .getBooks(authorId)
            .flatMap { getBookViewModels(it) }
    }

    private fun getBookViewModels(bookList: List<Book>): Single<List<BookViewModel>> {
        return Single.create<List<BookViewModel>> {
            val bookViewModels = mutableListOf<BookViewModel>()
            for (book in bookList) {
                bookViewModels.add(BookViewModel(book, utils))
            }
            it.onSuccess(bookViewModels)
        }
    }

    private fun getAuthorViewModels(authorList: List<Author>): Single<List<AuthorViewModel>> {
        return Single.create<List<AuthorViewModel>> {
            val authorViewModels = mutableListOf<AuthorViewModel>()
            for (i in authorList.indices) {
                val author = authorList[i]
                val authorViewModel = AuthorViewModel((author))
                authorViewModels.add(AuthorViewModel((author)))

                authorSelected.value?.let { authorId ->
                    if (authorId == authorViewModel.author.id) {
                        checkedPosition = i
                    }
                }
            }
            it.onSuccess(authorViewModels)
        }
    }

}