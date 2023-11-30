package com.coffee.homerista.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.coffee.homerista.data.entities.Record

@Dao
interface RecordDao {
    @Query("SELECT * FROM Record")
    fun getAll(): List<Record>

    @Query("SELECT * FROM record WHERE id IN (:recordIds)")
    fun loadAllByIds(recordIds: IntArray): List<Record>

    @Query("SELECT * FROM record WHERE date= :date")
    fun loadAllByDate(date: Long): List<Record>

    @Insert
    fun insertAll(vararg records: Record)

    @Update
    fun update(record: Record)

    @Delete
    fun delete(record: Record)
}