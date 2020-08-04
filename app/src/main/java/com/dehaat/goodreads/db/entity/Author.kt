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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dehaat.goodreads.utils.GlobalConfig
import com.dehaat.goodreads.utils.GlobalConfig.DB.Author.COLUMN_BIO
import com.dehaat.goodreads.utils.GlobalConfig.DB.Author.COLUMN_NAME

/**
 * Author Table
 */
@Entity(tableName = GlobalConfig.DB.Author.TABLE_NAME)
data class Author(
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_BIO) val bio: String) {
    @ColumnInfo(name = GlobalConfig.DB.COLUMN_ID)
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}