package com.coffee.homerista

import android.app.Application
import com.coffee.homerista.data.db.HomRistaDatabase
import com.coffee.homerista.repository.BeanRepository

class MyApplication : Application() {

    lateinit var beanRepository: BeanRepository

    override fun onCreate() {
        super.onCreate()

        val database = HomRistaDatabase.getInstance(this)
        beanRepository = BeanRepository(database!!.beanDao())
    }
}