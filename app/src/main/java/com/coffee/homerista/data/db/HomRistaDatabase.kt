package com.coffee.homerista.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.coffee.homerista.data.dao.BeanDao
import com.coffee.homerista.data.dao.RecordDao
import com.coffee.homerista.data.entities.Bean
import com.coffee.homerista.data.entities.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

@Database(entities = [Bean::class, Record::class], version = 1)
@TypeConverters(Converters::class)
abstract class HomRistaDatabase: RoomDatabase() {
    abstract fun beanDao(): BeanDao
    abstract fun recordDao(): RecordDao

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

                        val initRecordLisst = listOf(
                            Record(3, 20, 92.5, 882.98, 32.5, "에스 쇼콜라", Date(), "손이 시려울 땐 커피 한잔", 4, "공학관 히터 안 틀어주나요? 손이 너무 시려워서 코딩을 못 하겠어요"),
                            Record(2, 20, 99.5, 832.75, 35.0, "프루티 봉봉", Date(Date().time - 24 * 60 * 60 * 1000), "할아버지와 함께 마신 커피", 2, "할아버지는 커피가 싫다고 하셨어"),
                            Record(2, 45, 90.0, 853.25, 28.5, "게샤빌리지 오마", Date(Date().time - 2 *  24 * 60 * 60 * 1000), "레스티오보다 맛있다", 4, "레스티오보다 맛있는 커피."),
                            Record(4, 30, 98.3, 706.14, 25.2, "비야 베툴리아", Date(Date().time - 3 *  24 * 60 * 60 * 1000), "비싼 원두", 4, "역시 비싼 원두는 더 맛있구나"),
                            Record(2, 30, 88.5, 1000.17, 25.0, "엠부 무루에", Date(Date().time - 4 *  24 * 60 * 60 * 1000), "로봇 노래 좋다", 4, "로봇 BMG 선곡 미쳤다")
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

                                        recordDao().insertAll(
                                            *initRecordLisst.toTypedArray()
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