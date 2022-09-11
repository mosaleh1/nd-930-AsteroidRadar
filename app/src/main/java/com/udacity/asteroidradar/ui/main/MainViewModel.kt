package com.udacity.asteroidradar.ui.main


import androidx.lifecycle.*
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.data.MainRepository
import com.udacity.asteroidradar.data.api.get7DaysLaterDateFormatted
import com.udacity.asteroidradar.data.api.getTodayFormatted
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: MainRepository) :
    ViewModel() {

    private val _asteroidsFilter = MutableLiveData<FilterUtil>(FilterUtil.VIEW_SAVED_ASTEROIDS)

    val asteroidsLiveData: LiveData<List<Asteroid>> =
        _asteroidsFilter.switchMap { asteroidFilter ->
            when (asteroidFilter) {
                FilterUtil.VIEW_SAVED_ASTEROIDS -> {
                    repository.getAll()
                }
                FilterUtil.VIEW_TODAY_ASTEROIDS -> {
                    repository.getTodayAsteroid(getTodayFormatted(), getTodayFormatted())
                }
                FilterUtil.VIEW_NEXT_WEEK_ASTEROIDS -> {
                    repository.getTodayAsteroid(getTodayFormatted(), get7DaysLaterDateFormatted())
                }
                else -> {
                    repository.getAll()
                }

            }
        }


    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay get() = _pictureOfDay

    init {
        //createDummyData()
        viewModelScope.launch {
            _pictureOfDay.postValue(repository.getPictureOfDayAsync())
        }
    }


    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val count = repository.getCount()
            if (count == 0) {
                repository.getAllAsync(getTodayFormatted(), get7DaysLaterDateFormatted())
            }
        }
    }

    fun updateFilter(filter: FilterUtil) {
        _asteroidsFilter.value = filter
    }
}
//    private fun createDummyData() {
//        var items = listOf<Asteroid>()
//        for (i in 1..20) {
//            items = items.plus(
//                Asteroid(
//                    i.toLong(), "Code 45${i * i}", "2-2-2022",
//                    15.941 * i, 152.941,
//                    8.5 * i, 455.5 * i, i % 2 == 0
//                )
//            )
//        }
//        _asteroidsLiveData.postValue(items)
//    }
