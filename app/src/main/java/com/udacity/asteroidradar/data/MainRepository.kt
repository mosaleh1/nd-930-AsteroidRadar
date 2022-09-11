package com.udacity.asteroidradar.data

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.data.api.get7DaysLaterDateFormatted
import com.udacity.asteroidradar.data.api.getTodayFormatted
import com.udacity.asteroidradar.data.local.AsteroidDao
import com.udacity.asteroidradar.model.Asteroid

interface MainRepository {

    suspend fun getAllAsync(
        startDate: String = getTodayFormatted(),
        endDate: String = get7DaysLaterDateFormatted()
    )

    suspend fun getPictureOfDayAsync(): PictureOfDay?

    fun getAll(): LiveData<List<Asteroid>>

    fun getTodayAsteroid(startDate: String, closeDate: String): LiveData<List<Asteroid>>

    fun deleteAll()

    fun insertAll(asteroid: List<Asteroid>)

    fun getCount(): Int
}