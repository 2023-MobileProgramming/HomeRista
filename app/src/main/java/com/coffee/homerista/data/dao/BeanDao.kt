package com.coffee.homerista.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.coffee.homerista.data.entities.Bean

@Dao
interface BeanDao {
    @Query("SELECT * FROM bean")
    fun getAll(): List<Bean>

    @Query("SELECT * FROM bean WHERE id IN (:beanIds)")
    fun loadAllByIds(beanIds: IntArray): List<Bean>

    @Insert
    fun insertAll(vararg beans: Bean)

    @Update
    fun update(bean: Bean)

    @Delete
    fun delete(bean: Bean)
}