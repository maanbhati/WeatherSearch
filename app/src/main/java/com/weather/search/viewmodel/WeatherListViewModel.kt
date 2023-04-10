package com.weather.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.network.dto.CurrentWeatherResponse
import com.weather.network.dto.FutureWeatherResponse
import com.weather.search.data.helper.ModelConverter
import com.weather.search.data.local.WeatherEntityModel
import com.weather.search.data.remote.Resource
import com.weather.search.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.weather.search.data.remote.Resource.Status.LOADING
import com.weather.search.data.remote.Resource.Status.ERROR
import com.weather.search.data.remote.Resource.Status.SUCCESS
import com.weather.search.data.remote.WeatherDomainViewModel
import kotlinx.coroutines.flow.*

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherRepositoryImpl: WeatherRepository,
    private val modelConverter: ModelConverter
) : ViewModel() {

    private val _isLoadingContentState = MutableStateFlow(true)
    val isLoadingContentState: StateFlow<Boolean>
        get() = _isLoadingContentState

    private val _isErrorContentState = MutableStateFlow(true)
    val isErrorContentState: StateFlow<Boolean>
        get() = _isErrorContentState

    private val _successContentState =
        MutableStateFlow<WeatherDomainViewModel?>(null)
    val successContentState: StateFlow<WeatherDomainViewModel?>
        get() = _successContentState

    init {
        getSavedWeather()
    }

    fun fetchWeatherData(query: String) = viewModelScope.launch {
        onResource(Resource.loading())
        weatherRepositoryImpl.getCurrentWeather(query).flowOn(Dispatchers.IO)
            .catch { onResource(Resource.error()) }
            .collect { currentWeather -> currentWeather?.let { getFutureWeather(currentWeather) } }
    }

    private suspend fun getFutureWeather(currentWeatherResponse: CurrentWeatherResponse) {
        weatherRepositoryImpl.getFutureWeather(
            currentWeatherResponse.coord.lat.toString(),
            currentWeatherResponse.coord.lon.toString()
        ).flowOn(Dispatchers.IO)
            .catch { onResource(Resource.error()) }
            .collect { futureWeatherResponse ->
                futureWeatherResponse?.let {
                    handleWeatherResponse(currentWeatherResponse, futureWeatherResponse)
                }
            }
    }

    private fun handleWeatherResponse(
        currentWeatherResponse: CurrentWeatherResponse,
        futureWeatherResponse: FutureWeatherResponse
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherEntityModel =
                    modelConverter.convertFromDomainToEntityModel(
                        currentWeatherResponse,
                        futureWeatherResponse
                    )
                updateWeatherData(weatherEntityModel)
                onResource(
                    Resource.success(
                        modelConverter.convertEntityToDomainModel(
                            weatherEntityModel
                        )
                    )
                )
            } catch (error: Exception) {
                onResource(Resource.error(error.message))
            }
        }
    }

    suspend fun updateWeatherData(weatherEntityModel: WeatherEntityModel) =
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepositoryImpl.deleteAllWeather()
            weatherRepositoryImpl.insertWeather(weatherEntityModel)
        }

    fun getSavedWeather() = viewModelScope.launch {
        onResource(Resource.loading())
        weatherRepositoryImpl.getSavedWeather().flowOn(Dispatchers.IO)
            .catch { onResource(Resource.error()) }
            .collect {
                val weatherDomainModel = modelConverter.convertEntityToDomainModel(it)
                onResource(Resource.success(weatherDomainModel))
            }
    }

    private fun onResource(weatherState: Resource<WeatherDomainViewModel>) {
        when (weatherState.status) {
            LOADING -> {
                _isLoadingContentState.value = true
                _isErrorContentState.value = false
                _successContentState.value = null
            }
            SUCCESS -> {
                _isLoadingContentState.value = false
                _isErrorContentState.value = false
                _successContentState.value = weatherState.data
            }
            ERROR -> {
                _isLoadingContentState.value = false
                _isErrorContentState.value = true
            }
        }
    }
}