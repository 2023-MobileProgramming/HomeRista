package com.coffee.homerista

import android.app.Application
import com.coffee.homerista.data.db.HomRistaDatabase
import com.coffee.homerista.repository.BeanRepository
import com.coffee.homerista.repository.RecordRepository

class MyApplication : Application() {

    lateinit var beanRepository: BeanRepository
    lateinit var recordRepository: RecordRepository

    override fun onCreate() {
        super.onCreate()

        val database = HomRistaDatabase.getInstance(this)
        beanRepository = BeanRepository(database!!.beanDao())
        recordRepository = RecordRepository(database!!.recordDao())
    }
}