package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.workers.FetchDailyAsteroidsWorker
import java.time.Duration
import java.util.concurrent.TimeUnit

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val dailyFetchRequest =
            PeriodicWorkRequestBuilder<FetchDailyAsteroidsWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.DAYS)
                .addTag(Constants.WORKER_TAG)
                .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            Constants.WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            dailyFetchRequest
        )
    }
}