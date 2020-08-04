package com.dehaat.goodreads.db.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    private const val pattern = "dd-mm-yyyy"
    private val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)

    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): Date? {
        return value?.let {
            simpleDateFormat.parse(it)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromDeliveryType(date: Date?): String? {
        return date?.let {
            simpleDateFormat.format(date)
        }
    }

}