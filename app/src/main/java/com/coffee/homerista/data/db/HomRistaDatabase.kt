package com.coffee.homerista.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.coffee.homerista.data.dao.BeanDao
import com.coffee.homerista.data.entities.Bean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                    println(context.applicationContext)
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HomRistaDatabase::class.java,
                        "homrista"
                    ).addCallback(object : Callback() {

                        val initBeanList = listOf(
                            Bean("에스 쇼콜라", "브라질, 콜롬비아, 에티오피아", "블렌딩", "다크초콜릿, 밸런스, 크리미", 70, 40, 20, 80),
                            Bean("프루티 봉봉", "에티오피아, 케냐", "블렌딩", "베르가못, 자스민, 만다린, 카라멜 ", 40, 50, 70, 52),
                            Bean("게샤빌리지 오마", "에티오피아", "내추럴", "장미, 딸기, 자두, 바닐라", 46, 60, 40, 55),
                            Bean("비야 베툴리아", "콜롬비아", "블렌딩", "패션후르츠, 포도, 초콜레티, 로즈마리", 50, 30, 40, 50),
                            Bean("엠부 무루에", "케냐", "워시드", "자몽, 커런트, 베르가못, 블랙티", 30, 20, 40, 55)
                        )

                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            GlobalScope.launch {
                                withContext(Dispatchers.IO) {
                                    getInstance(context)?.run {
                                        //원두 초기값 설정
                                        beanDao().insertAll(
                                            *initBeanList.toTypedArray()
                                        )
                                    }
                                }
                            }
                        }
                    }).build()
                }
            }
            return instance
        }
    }
}