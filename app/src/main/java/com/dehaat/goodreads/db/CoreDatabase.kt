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

package com.dehaat.goodreads.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dehaat.goodreads.db.dao.AuthorDao
import com.dehaat.goodreads.db.dao.BookDao
import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.utils.GlobalConfig.DB.DATABASE_NAME
import com.dehaat.goodreads.utils.GlobalConfig.DB.DATABASE_VERSION

/**
 * Core class to access complete database
 */
@Database(entities = [Author::class, Book::class], version = DATABASE_VERSION, exportSchema = false)
abstract class CoreDatabase : RoomDatabase() {
    abstract fun authorDao(): AuthorDao
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile private var INSTANCE: CoreDatabase? = null

        fun getInstance(context: Context): CoreDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, CoreDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
    }

}
