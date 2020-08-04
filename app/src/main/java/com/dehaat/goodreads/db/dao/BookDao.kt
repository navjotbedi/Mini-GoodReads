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

package com.dehaat.goodreads.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_AUTHOR_ID
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.TABLE_NAME

/**
 * Database operations for Book
 */
@Dao
interface BookDao {

    /**
     *  Delete all the Books
     */
    @Query("DELETE FROM $TABLE_NAME")
    fun clearAll()

    /**
     * Select all Books
     *
     * @return All the books in the table.
     */
    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_AUTHOR_ID = :authorId")
    fun getAll(authorId: Long): List<Book>

    /**
     *  Insert the books in the table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(books: List<Book>)

}