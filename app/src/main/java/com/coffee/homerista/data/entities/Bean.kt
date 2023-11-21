package com.coffee.homerista.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bean (
    val name: String,
    val countries: String,
    val processing: String,
    val cupNote: String,
    val body: Short,
    val sweetSalty: Short,
    val bitterSour: Short,
    val darkLight: Short
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 //id값 자동 생성
}