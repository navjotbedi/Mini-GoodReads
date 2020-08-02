package com.dehaat.goodreads.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dehaat.goodreads.utils.GlobalConfig
import com.dehaat.goodreads.utils.GlobalConfig.DB.Author.COLUMN_BIO
import com.dehaat.goodreads.utils.GlobalConfig.DB.Author.COLUMN_NAME

@Entity(tableName = GlobalConfig.DB.Author.TABLE_NAME)
data class Author(
    @ColumnInfo(name = GlobalConfig.DB.COLUMN_ID) @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_BIO) val bio: String
)