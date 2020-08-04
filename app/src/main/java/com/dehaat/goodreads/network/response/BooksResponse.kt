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

package com.dehaat.goodreads.network.response

import com.squareup.moshi.Json
import java.util.*

data class BooksResponse(@Json(name = "data") val authorsResponseList: List<AuthorResponse>) {

    data class AuthorResponse(@Json(name = "author_name") val name: String, @Json(name = "author_bio") val bio: String, @Json(name = "books") val booksResponseList: List<BookResponse>) {

        data class BookResponse(@Json(name = "published_date") val publishedDate: Date?,
                                @Json(name = "publisher") val publisher: String,
                                @Json(name = "title") val title: String,
                                @Json(name = "description") val description: String,
                                @Json(name = "price") val price: String)

    }

}