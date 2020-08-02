package com.dehaat.goodreads.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_AUTHOR_ID
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.TABLE_NAME

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
    fun insertAll(trigger: List<Book>)

}