package com.udacity.asteroidradar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.data.MainRepositoryImpl
import com.udacity.asteroidradar.data.api.AsteroidClient
import com.udacity.asteroidradar.data.local.AsteroidDatabase
import com.udacity.asteroidradar.ui.main.MainViewModel

class ViewModelFactory(
    private val dataSource: AsteroidDatabase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val repo = MainRepositoryImpl(dataSource.asteroidDao,AsteroidClient.getRetrofitClient())
            return MainViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}