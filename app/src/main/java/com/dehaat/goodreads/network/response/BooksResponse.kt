package com.dehaat.goodreads.network.response

import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.db.entity.Book
import com.squareup.moshi.Json

data class BooksResponse(@Json(name = "data") val authorsResponseList: List<AuthorResponse>) {

    data class AuthorResponse(
        @Json(name = "author_name") val name: String,
        @Json(name = "author_bio") val bio: String,
        @Json(name = "books") val booksResponseList: List<BookResponse>
    ) {

        data class BookResponse(
            @Json(name = "published_date") val publishedDate: String,
            @Json(name = "publisher") val publisher: String,
            @Json(name = "title") val title: String,
            @Json(name = "description") val description: String,
            @Json(name = "price") val price: String
        )

    }

    fun processResponse(): Pair<List<Author>, List<Book>>{
        val authorList = mutableListOf<Author>()
        val bookList = mutableListOf<Book>()
        for(authorResponse in authorsResponseList){
            authorList.add(Author(authorResponse.name, authorResponse.bio))

            for(bookResponse in authorResponse.booksResponseList) {
                bookList.add(Book(1, bookResponse.title, bookResponse.publisher, null, bookResponse.description, bookResponse.price))
            }
        }

        return Pair(authorList, bookList)
    }

}