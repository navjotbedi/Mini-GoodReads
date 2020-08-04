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

package com.dehaat.goodreads.db.entity

import androidx.room.*
import com.dehaat.goodreads.db.converter.DateConverter
import com.dehaat.goodreads.utils.GlobalConfig
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_AUTHOR_ID
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_DESCRIPTION
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_PRICE
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_PUBLISHED_DATE
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_PUBLISHER
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_TITLE
import com.dehaat.goodreads.utils.GlobalConfig.DB.COLUMN_ID
import java.util.*

/**
 * Book Table
 */
@Entity(tableName = GlobalConfig.DB.Book.TABLE_NAME)
@ForeignKey(entity = Author::class, parentColumns = [COLUMN_ID], childColumns = [COLUMN_AUTHOR_ID], onDelete = ForeignKey.CASCADE)
@TypeConverters(DateConverter::class)
class Book(@ColumnInfo(name = COLUMN_AUTHOR_ID) val authorId: Long,
           @ColumnInfo(name = COLUMN_TITLE) val title: String,
           @ColumnInfo(name = COLUMN_PUBLISHER) val publisherName: String,
           @ColumnInfo(name = COLUMN_PUBLISHED_DATE) val publishedDate: Date?,
           @ColumnInfo(name = COLUMN_DESCRIPTION) val description: String,
           @ColumnInfo(name = COLUMN_PRICE) val price: String) {
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}