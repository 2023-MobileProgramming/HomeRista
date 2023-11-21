package com.coffee.homerista.repository

import android.app.Application
import com.coffee.homerista.data.db.HomRistaDatabase
import com.coffee.homerista.data.entities.Bean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BeanRepository(application: Application) {
    private val homRistaDatabase = HomRistaDatabase.getInstance(application)!!
    private val beanDao = homRistaDatabase.beanDao()
    suspend fun getAll(): List<Bean> {
        return withContext(Dispatchers.IO) {
            beanDao.getAll()
        }
    }
}