package com.coffee.homerista.repository

import com.coffee.homerista.data.dao.BeanDao
import com.coffee.homerista.data.entities.Bean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BeanRepository(val beanDao: BeanDao) {
    suspend fun getAll(): List<Bean> {
        return withContext(Dispatchers.IO) {
            beanDao.getAll()
        }
    }

    suspend fun insert(vararg beans: Bean) {
        withContext(Dispatchers.IO) {
            beanDao.insertAll(*beans)
        }
    }

    suspend fun update(bean: Bean) {
        withContext(Dispatchers.IO) {
            beanDao.update(bean)
        }
    }

    suspend fun delete(bean: Bean) {
        withContext(Dispatchers.IO) {
            beanDao.delete(bean)
        }
    }
}