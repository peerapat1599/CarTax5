package com.example.cartax5

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cartax5.database.CarDatabase
import com.example.cartax5.database.CarDatabaseModels
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope


class CarInsertModels(application: Application) : AndroidViewModel(application) {


    private val repository: CarInsertRespository
    val allCar: LiveData<List<CarDatabaseModels>>

    init {
        val wordsDao = CarDatabase.getDatabase(application.applicationContext).carDatabaseDAO()
        repository = CarInsertRespository(wordsDao)
        allCar = repository.allCar

    }

    fun insert(word: CarDatabaseModels) = viewModelScope.launch {
        repository.insert(word)
    }


    fun clear(item: String) = viewModelScope.launch {
        repository.clear(item)
    }

    fun update(id: String, item: String, age: Int) = viewModelScope.launch {
        repository.update(id, item, age)
    }

    fun clearAll() = viewModelScope.launch {
        repository.clearAll()
    }

}