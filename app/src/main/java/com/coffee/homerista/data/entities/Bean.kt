package com.coffee.homerista.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Bean (
    var name: String,
    var countries: String,
    var processing: String,
    var cupNote: String,
    var body: Int,
    var sweetSalty: Int,
    var bitterSour: Int,
    var darkLight: Int
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 //id값 자동 생성
}