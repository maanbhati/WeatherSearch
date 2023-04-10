package com.weather.search.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weather.network.api.Api
import com.weather.network.dto.CurrentWeatherResponse
import com.weather.network.dto.FutureWeatherResponse
import com.weather.search.data.local.WeatherDao
import com.weather.search.data.local.WeatherEntityModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var api: Api

    @MockK
    private lateinit var weatherDao: WeatherDao

    @MockK
    private lateinit var weatherEntityModel: WeatherEntityModel

    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        repository = WeatherRepositoryImpl(api, weatherDao)
    }

    @Test
    fun when_get_current_weather_called_verify_returns_result_from_api() = runTest {
        val query = "query"
        val response = CurrentWeatherResponse()
        every { runBlocking { api.getCurrentWeather(query) } } returns Response.success(response)

        val result = repository.getCurrentWeather(query).first()

        assertEquals(response, result)
    }

    @Test(expected = IOException::class)
    fun when_get_current_weather_called_verify_error_expected_from_api() = runTest {
        val query = "query"
        every { runBlocking { api.getCurrentWeather(query) } } returns Response.error(
            400,
            byteArrayOf().toResponseBody()
        )

        repository.getCurrentWeather(query).first()
    }

    @Test
    fun when_get_future_weather_called_verify_returns_result_from_api() = runTest {
        val lat = "12345"
        val long = "67892"
        val response = FutureWeatherResponse()
        every { runBlocking { api.getFutureWeather(lat, long) } } returns Response.success(response)

        val result = repository.getFutureWeather(lat, long).first()

        assertEquals(response, result)
    }

    @Test(expected = IOException::class)
    fun when_get_future_weather_called_verify_error_expected_from_api() = runTest {
        val lat = "12345"
        val long = "67892"
        every { runBlocking { api.getFutureWeather(lat, long) } } returns Response.error(
            400,
            byteArrayOf().toResponseBody()
        )

        repository.getFutureWeather(lat, long).first()
    }

    @Test
    fun when_get_saved_weather_called_verify_returns_saved_data_from_dao() = runTest {
        every { runBlocking { weatherDao.getSavedWeather() } } returns flowOf(weatherEntityModel)

        val result = repository.getSavedWeather().first()

        assertEquals(weatherEntityModel, result)
    }

    @Test
    fun when_insert_weather_called_verify_method_call_from_dao() = runTest {
        repository.insertWeather(weatherEntityModel)

        verify { runBlocking { weatherDao.insert(weatherEntityModel) } }
    }

    @Test
    fun when_delete_weather_called_verify_method_call_from_dao() = runTest {
        repository.deleteAllWeather()

        verify { runBlocking { weatherDao.deleteAll() } }
    }
}