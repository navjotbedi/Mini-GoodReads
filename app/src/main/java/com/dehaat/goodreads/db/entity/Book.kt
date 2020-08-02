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

@Entity(tableName = GlobalConfig.DB.Book.TABLE_NAME)
@ForeignKey(
    entity = Author::class,
    parentColumns = [COLUMN_ID],
    childColumns = [COLUMN_AUTHOR_ID],
    onDelete = ForeignKey.CASCADE
)
@TypeConverters(DateConverter::class)
class Book(
    @ColumnInfo(name = COLUMN_ID) @PrimaryKey(autoGenerate = true) val id: Long,

    @ColumnInfo(name = COLUMN_AUTHOR_ID) val userId: Int,
    @ColumnInfo(name = COLUMN_TITLE) val title: String,
    @ColumnInfo(name = COLUMN_PUBLISHER) val publisherName: String,
    @ColumnInfo(name = COLUMN_PUBLISHED_DATE) val publishedDate: Date?,
    @ColumnInfo(name = COLUMN_DESCRIPTION) val description: String,
    @ColumnInfo(name = COLUMN_PRICE) val price: String
)