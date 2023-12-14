package com.coffee.homerista.repository

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.coffee.homerista.data.dao.RecordDao
import com.coffee.homerista.data.entities.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth


class RecordRepository(val recordDao: RecordDao) {
    suspend fun getAll(): List<Record> {
        return withContext(Dispatchers.IO) {
            recordDao.getAll()
        }
    }

    @SuppressLint("NewApi")
    suspend fun getAllByDate(date: LocalDate): List<Record> {
        return withContext(Dispatchers.IO) {
            recordDao.loadAllByDate(date.toEpochDay())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllByMonth(date: LocalDate): List<Record> {
        return withContext(Dispatchers.IO) {
            val yearOfMonth = YearMonth.of(date.year, date.month)
            recordDao.loadAllByMonth(yearOfMonth.atDay(1).toEpochDay() ,yearOfMonth.atEndOfMonth().toEpochDay())
        }
    }

    suspend fun insert(vararg records: Record) {
        withContext(Dispatchers.IO) {
            recordDao.insertAll(*records)
        }
    }

    suspend fun update(record: Record) {
        withContext(Dispatchers.IO) {
            recordDao.update(record)
        }
    }

    suspend fun delete(record: Record) {
        withContext(Dispatchers.IO) {
            recordDao.delete(record)
        }
    }
}