package com.udacity.asteroidradar.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.MainRepositoryImpl
import com.udacity.asteroidradar.data.api.AsteroidClient
import com.udacity.asteroidradar.data.local.AsteroidDatabase


class FetchDailyAsteroidsWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(
    context,
    workerParams
) {

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext).asteroidDao
        val count = database.getCount()
        if (count > 0) {
            database.deleteAll()
        }
        val repository = MainRepositoryImpl(
            database, AsteroidClient.getRetrofitClient()
        )
        return try {
            repository.getAllAsync()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}