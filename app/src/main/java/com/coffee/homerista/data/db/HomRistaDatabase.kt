package com.coffee.homerista.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.coffee.homerista.data.dao.BeanDao
import com.coffee.homerista.data.entities.Bean

@Database(entities = [Bean::class], version = 1)
abstract class HomRistaDatabase: RoomDatabase() {
    abstract fun beanDao(): BeanDao

    //데이터베이스 싱글톤으로 생성
    companion object {
        private var instance: HomRistaDatabase? = null

        @Synchronized
        fun getInstance(context: Context): HomRistaDatabase? {
            if (instance == null) {
                synchronized(HomRistaDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HomRistaDatabase::class.java,
                        "homrista-database"
                    ).build()
                }
            }
            return instance
        }
    }
}