package com.sunasterisk.appchat.db.persistent.converters

import androidx.room.TypeConverter
import java.util.*

class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? =
        value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? =
        date?.time?.toLong()
}
