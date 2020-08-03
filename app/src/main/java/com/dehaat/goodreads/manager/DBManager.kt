package com.dehaat.goodreads.manager

import android.content.Context
import com.dehaat.goodreads.db.CoreDatabase
import com.dehaat.goodreads.db.dao.AuthorDao
import com.dehaat.goodreads.db.dao.BookDao
import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.network.response.BooksResponse
import dagger.hilt.android.qualifiers.ActivityContext
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DBManager @Inject constructor(@ActivityContext private val context: Context){

    private val bookDao: BookDao
    private val authorDao: AuthorDao

    init {
        val coreDatabase = CoreDatabase.getInstance(context)
        bookDao = coreDatabase.bookDao()
        authorDao = coreDatabase.authorDao()
    }

    fun fillDb(response : BooksResponse): Single<Unit> {
        return Single.just(response)
            .map {
                val response = it.processResponse()
                authorDao.insertAll(response.first)
                bookDao.insertAll(response.second)
            }
    }

    fun getAuthors(): Single<List<Author>> {
        return Single.create<List<Author>> { observer ->
            observer.onSuccess(authorDao.getAll())
        }
    }

    fun getBooks(authorId: Long): Single<List<Book>> {
        return Single.just(authorId).map { bookDao.getAll(it) }
    }

}