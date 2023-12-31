package com.coffee.homerista.data.db

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @SuppressLint("NewApi")
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @SuppressLint("NewApi")
    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}