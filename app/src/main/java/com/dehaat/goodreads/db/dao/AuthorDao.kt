package com.dehaat.goodreads.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.utils.GlobalConfig.DB.Author.TABLE_NAME

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
    fun insertAll(authors: List<Author>)


}