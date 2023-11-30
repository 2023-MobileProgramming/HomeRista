package com.coffee.homerista.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDate

@Entity
data class Record (
    var min: Int,
    var sec: Int,
    var temp: Double,
    var decomp: Double,
    var weight: Double,
    var beanName: String,
    var date: LocalDate,
    var title: String,
    var rating: Int,
    var comment: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 //id값 자동 생성
}