package com.weather.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weather.network.dto.CurrentWeatherResponse
import com.weather.search.MainCoroutineTestRule
import com.weather.search.TestDispatcherProvider
import com.weather.search.core.DispatcherProvider
import com.weather.search.data.helper.ModelConverter
import com.weather.search.data.local.WeatherEntityModel
import com.weather.search.repository.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineTestRule()

    @MockK
    private lateinit var repository: WeatherRepository

    @MockK
    private lateinit var dispatcherProvider: DispatcherProvider

    @MockK
    private lateinit var modelConverter: ModelConverter

    @MockK
    private lateinit var weatherEntityModel: WeatherEntityModel

    @MockK
    private lateinit var currentWeatherResponse: CurrentWeatherResponse

    private lateinit var viewModel: WeatherListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        dispatcherProvider = TestDispatcherProvider()
        viewModel = WeatherListViewModel(repository, modelConverter, dispatcherProvider)
    }

    @Test
    fun when_get_saved_weather_called_verify_method_call_from_repository() = runTest {
        every { runBlocking { repository.getSavedWeather() } } returns flowOf(weatherEntityModel)

        viewModel.getSavedWeather()

        verify { runBlocking { repository.getSavedWeather() } }
    }

    @Test
    fun when_search_weather_called_verify_method_call_from_repository() = runTest {
        val query = "query"
        viewModel.fetchWeatherData(query)

        verify { runBlocking { repository.getCurrentWeather(query) } }
    }

    @Test
    fun when_update_weather_called_verify_method_call_from_repository() = runTest {
        viewModel.updateWeatherData(weatherEntityModel)

        verify { runBlocking { repository.deleteAllWeather() } }
        verify { runBlocking { repository.insertWeather(weatherEntityModel) } }
    }

    @Test
    fun when_search_weather_called_verify_nested_method_call_with_resulted_lat_long_data_from_repository() =
        runTest {
            val query = "query"
            val lat = 73.22
            val lon = 74.33
            every { runBlocking { currentWeatherResponse.coord.lat } } returns lat
            every { runBlocking { currentWeatherResponse.coord.lon } } returns lon
            every { runBlocking { repository.getCurrentWeather(query) } } returns flowOf(
                currentWeatherResponse
            )
            viewModel.fetchWeatherData(query)

            verify { runBlocking { repository.getFutureWeather(lat.toString(), lon.toString()) } }
        }

    @Test
    fun when_get_saved_weather_called_do_not_call_api_method_from_repository() =
        runTest {
            val query = "query"
            viewModel.getSavedWeather()

            verify(inverse = true) { runBlocking { repository.getCurrentWeather(query) } }
        }

    @Test
    fun when_get_saved_weather_called_do_not_call_delete_method_from_repository() =
        runTest {
            viewModel.getSavedWeather()

            verify(inverse = true) { runBlocking { repository.deleteAllWeather() } }
        }
}