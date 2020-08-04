package com.dehaat.goodreads.network.response

import com.squareup.moshi.Json

data class BooksResponse(@Json(name = "data") val authorsResponseList: List<AuthorResponse>) {

    data class AuthorResponse(@Json(name = "author_name") val name: String, @Json(name = "author_bio") val bio: String, @Json(name = "books") val booksResponseList: List<BookResponse>) {

        data class BookResponse(@Json(name = "published_date") val publishedDate: String,
                                @Json(name = "publisher") val publisher: String,
                                @Json(name = "title") val title: String,
                                @Json(name = "description") val description: String,
                                @Json(name = "price") val price: String)

    }

}