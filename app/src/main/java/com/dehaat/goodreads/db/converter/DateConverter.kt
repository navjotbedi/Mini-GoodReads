package com.dehaat.goodreads.db.converter

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): Date? {
        return if (value != null) null
        else return null
    }

    @TypeConverter
    @JvmStatic
    fun fromDeliveryType(date: Date): String {
        return "deliveryType.value"
    }

}