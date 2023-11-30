package com.coffee.homerista.repository

import com.coffee.homerista.data.dao.RecordDao
import com.coffee.homerista.data.entities.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class RecordRepository(val recordDao: RecordDao) {
    suspend fun getAll(): List<Record> {
        return withContext(Dispatchers.IO) {
            recordDao.getAll()
        }
    }

    suspend fun getAllByDate(date: Date): List<Record> {
        return withContext(Dispatchers.IO) {
            recordDao.loadAllByDate(date.time)
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