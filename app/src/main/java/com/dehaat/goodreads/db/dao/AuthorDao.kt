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
import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.utils.GlobalConfig.DB.Author.TABLE_NAME

/**
 * Database operations for Author
 */
@Dao
interface AuthorDao {

    /**
     *  Delete all the Authors
     */
    @Query("DELETE FROM $TABLE_NAME")
    fun clearAll()

    /**
     * Select all Authors
     *
     * @return All the authors in the table.
     */
    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): List<Author>

    /**
     *  Insert the Authors in the table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(author: Author): Long

}