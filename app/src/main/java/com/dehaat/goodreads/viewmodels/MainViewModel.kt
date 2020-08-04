package com.dehaat.goodreads.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.manager.DBManager
import com.dehaat.goodreads.network.RestApi

class MainViewModel @ViewModelInject constructor(private val dbManager: DBManager,
                                                 private val restApi: RestApi) : ViewModel() {

    val selected = MutableLiveData<Long>()

    fun select(authorId: Long) {
        selected.value = authorId
    }

//    val authors: Flowable<List<AuthorViewModel>> = dbManager.getAuthors()
//        .flatMap { getAuthorViewModels(it) }
//        .mergeWith(
//            restApi.getBooks()
//                .flatMap { dbManager.fillDb(it) }
//                .flatMap { dbManager.getAuthors() }
//                .flatMap { getAuthorViewModels(it) }
//        )
//
//    private val authorViewModels: MutableLiveData<List<AuthorViewModel>> by lazy {
//        MutableLiveData<List<AuthorViewModel>>().also{
//            loadAuthors()
//        }
//    }
//
//    fun getAuthors(): LiveData<List<AuthorViewModel>>{
//        return authorViewModels
//    }
//
//    private fun loadAuthors(){
//        dbManager.getAuthors()
//            .flatMap { getAuthorViewModels(it) }
//            .mergeWith(
//                restApi.getBooks()
//                    .flatMap { dbManager.fillDb(it) }
//                    .flatMap { dbManager.getAuthors() }
//                    .flatMap { getAuthorViewModels(it) }
//            )
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onNext = {
//
//                },
//                onError = {  })
//    }
//
//    private fun getAuthorViewModels(authorList: List<Author>): Single<List<AuthorViewModel>> {
//        return Single.create<List<AuthorViewModel>> {
//            val authorViewModels = mutableListOf<AuthorViewModel>()
//            for (author in authorList) {
//                authorViewModels.add(AuthorViewModel((author)))
//            }
//            it.onSuccess(authorViewModels)
//        }
//    }

}