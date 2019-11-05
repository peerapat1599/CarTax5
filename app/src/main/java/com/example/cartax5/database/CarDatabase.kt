package com.example.cartax5.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CarDatabaseModels::class), version = 1, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {

    abstract fun carDatabaseDAO(): CarDatabaseDAO


    companion object {
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(
            context: Context
        ): CarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "tax_car_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

