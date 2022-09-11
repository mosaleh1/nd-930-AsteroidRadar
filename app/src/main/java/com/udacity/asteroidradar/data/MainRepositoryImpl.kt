package com.udacity.asteroidradar.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.data.api.AsteroidApi
import com.udacity.asteroidradar.data.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.local.AsteroidDao
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await

class MainRepositoryImpl(
    private val database: AsteroidDao,
    private val apiAsteroid: AsteroidApi
) : MainRepository {

    companion object {
        private const val TAG = "MainRepositoryImpl"
    }

    override suspend fun getAllAsync(
        startDate: String,
        endDate: String
    ) {
        return try {
            var asteroids: List<Asteroid>
            withContext(Dispatchers.IO) {
                val response = apiAsteroid.getAllAsync(startDate, endDate).await()
                asteroids = parseAsteroidsJsonResult(JSONObject(response))
                Log.d(TAG, "getAllAsync: ${asteroids.size}")
                database.insertAll(asteroids)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getPictureOfDayAsync(): PictureOfDay? {
        return try {
            val pic = apiAsteroid.getPictureOfDayAsync().await()
            Log.d(TAG, "getPictureOfDayAsync: pic ${pic.url} ")
            if (pic.mediaType == "image")
                pic
            else
                null
        } catch (e: Exception) {
            null
        }


    }

    override fun insertAll(asteroid: List<Asteroid>) {
        database.insertAll(asteroid)
    }

    override fun getAll(): LiveData<List<Asteroid>> {
        return database.getAll()
    }

    override fun getTodayAsteroid(startDate: String, closeDate: String): LiveData<List<Asteroid>> {
        return database.getTodayAsteroid(startDate, closeDate)
    }

    override fun deleteAll() {
        database.deleteAll()
    }

    override fun getCount(): Int {
        return database.getCount()
    }
}