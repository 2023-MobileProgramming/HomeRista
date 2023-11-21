package com.coffee.homerista.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.coffee.homerista.data.entities.Bean

@Dao
interface BeanDao {
    @Query("SELECT * FROM bean")
    fun getAll(): List<Bean>

    @Query("SELECT * FROM bean WHERE id IN (:beanIds)")
    fun loadAllByIds(beanIds: IntArray): List<Bean>

//    @Query("SELECT * FROM bean WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg beans: Bean)

    @Delete
    fun delete(bean: Bean)
}