package com.example.cartax5.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_item")
data class CarDatabaseModels(

    @PrimaryKey

    @ColumnInfo(name = "carId")
    val carId: String,

    @ColumnInfo(name = "carType")
    var carType: String,

    @ColumnInfo(name = "carCC")
    var carCC: String,

    @ColumnInfo(name = "carWeight")
    var carWeight: String,

    @ColumnInfo(name = "ageCar")
    var ageCar: Int,

    @ColumnInfo(name = "dateExpired")
    var dateExpired: String,

    @ColumnInfo(name = "price")
    var price: Double

)
