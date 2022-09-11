package com.udacity.asteroidradar.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.Asteroid

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroid: List<Asteroid>)

    @Query("Select * from asteroid order by closeApproachDate")
    fun getAll(): LiveData<List<Asteroid>>

    @Query("SELECT COUNT(*) FROM asteroid")
    fun getCount(): Int

    @Query("delete from asteroid")
    fun deleteAll()

}