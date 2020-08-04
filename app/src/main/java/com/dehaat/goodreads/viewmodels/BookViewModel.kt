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

import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.utils.Utils

class BookViewModel(var book: Book, val utils: Utils) : ItemViewModel() {

    val price: String
        get() = "Price: ${book.price} INR"
    val description: String
        get() = book.description
    val publishedDate: String
        get() = "Published Date: ${book.publishedDate?.let { utils.getDateFormatter().format(it) } ?: ""}"
    val publisherName: String
        get() = "Publisher Name: ${book.publisherName}"
    val title: String
        get() = book.title

}