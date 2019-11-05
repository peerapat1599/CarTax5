package com.example.cartax5

import androidx.lifecycle.LiveData
import com.example.cartax5.database.CarDatabaseDAO
import com.example.cartax5.database.CarDatabaseModels

class CarInsertRespository(private val carDao: CarDatabaseDAO) {

    val allCar: LiveData<List<CarDatabaseModels>> = carDao.getAllCarId()

    fun insert(car: CarDatabaseModels) {
        carDao.insert(car)
    }

    fun clear(item: String) {
        carDao.delete(item)
    }

    fun update(id: String, item: String, age: Int) {
        carDao.update(id, item, age)
    }

    fun clearAll() {
        carDao.clearAll()
    }

}