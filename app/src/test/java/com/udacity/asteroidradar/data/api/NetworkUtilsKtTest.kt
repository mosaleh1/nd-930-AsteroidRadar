package com.udacity.asteroidradar.data.api


import com.google.common.truth.*
import com.google.common.truth.Truth.assertThat
import com.udacity.asteroidradar.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.junit.Test

class NetworkUtilsKtTest {

    @Test
    fun getTodayFormattedTest() {
        val text = getTodayFormatted()
        assertThat(text).isEqualTo("2022-09-11")
    }

    @Test
    fun getNext7DaysTest() {
        val text = get7DaysLaterDateFormatted()
        assertThat(text).isEqualTo("2022-09-17")
    }
//    @Test
//    fun getRetrofitClient() {
//        GlobalScope.launch(Dispatchers.Main) {
//
//            val data = AsteroidClient.getRetrofitClient()
//                .getAllAsync("7-9-2022", "10-9-2022", BuildConfig.API_KEY)
//                .await()
//
//            val asteroidsList = parseAsteroidsJsonResult(JSONObject(data.string()))
//            assert(asteroidsList.isNotEmpty())
//        }
//
//    }
}