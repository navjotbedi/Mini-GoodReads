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

/**
 * Manager to perform database operations
 */
class DBManager @Inject constructor(@ActivityContext private val context: Context) {

    private val bookDao: BookDao
    private val authorDao: AuthorDao
    private val coreDatabase: CoreDatabase = CoreDatabase.getInstance(context)

    init {
        bookDao = coreDatabase.bookDao()
        authorDao = coreDatabase.authorDao()
    }

    fun fillDb(rawResponse: BooksResponse): Single<Unit> {
        return Single.just(rawResponse).map {
//            coreDatabase.clearAllTables()
            val bookList = mutableListOf<Book>()
            for (authorResponse in it.authorsResponseList) {
                val authorId = authorDao.insert(Author(authorResponse.name, authorResponse.bio))

                for (bookResponse in authorResponse.booksResponseList) {
                    bookList.add(Book(authorId, bookResponse.title, bookResponse.publisher, null, bookResponse.description, bookResponse.price))
                }
            }
            bookList
        }.map {
            bookDao.insertAll(it)
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