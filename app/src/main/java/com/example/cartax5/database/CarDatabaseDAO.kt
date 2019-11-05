package com.example.cartax5.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CarDatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(car: CarDatabaseModels)

    @Query("UPDATE all_item SET dateExpired = :dateExpired,ageCar = :ageCar WHERE carId = :carId")
    fun update(carId: String, dateExpired: String, ageCar: Int): Int

    @Query("SELECT * FROM all_item ORDER BY carId ASC")
    fun getAllCarId(): LiveData<List<CarDatabaseModels>>


    @Query("SELECT * FROM all_item WHERE carId LIKE :title")
    fun findById(title: String): CarDatabaseModels


    @Query("DELETE FROM all_item WHERE carId = :carSelected ")
    fun delete(carSelected: String)

    @Query("DELETE FROM all_item ")
    fun clearAll()
}