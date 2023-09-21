package com.weather.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.network.dto.CurrentWeatherResponse
import com.weather.network.dto.FutureWeatherResponse
import com.weather.search.core.UiState
import com.weather.search.data.helper.ModelConverter
import com.weather.search.data.local.WeatherEntityModel
import com.weather.search.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.weather.search.data.remote.WeatherDomainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherRepositoryImpl: WeatherRepository,
    private val modelConverter: ModelConverter
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<WeatherDomainViewModel?>>(UiState.Loading)
    val uiState: StateFlow<UiState<WeatherDomainViewModel?>> = _uiState

    init {
        getSavedWeather()
    }

    fun fetchWeatherData(query: String) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        weatherRepositoryImpl.getCurrentWeather(query).flowOn(Dispatchers.IO)
            .catch { exception ->
                _uiState.value = UiState.Error(exception.toString())
            }.collect { currentWeather ->
                currentWeather?.let { getFutureWeather(currentWeather) }
            }
    }

    private suspend fun getFutureWeather(currentWeatherResponse: CurrentWeatherResponse) {
        weatherRepositoryImpl.getFutureWeather(
            currentWeatherResponse.coord.lat.toString(),
            currentWeatherResponse.coord.lon.toString()
        ).flowOn(Dispatchers.IO)
            .catch { exception ->
                _uiState.value = UiState.Error(exception.toString())
            }.collect { futureWeatherResponse ->
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
                val weatherEntityModel = modelConverter.convertFromDomainToEntityModel(
                    currentWeatherResponse,
                    futureWeatherResponse
                )
                updateWeatherData(weatherEntityModel)
                _uiState.value =
                    UiState.Success(modelConverter.convertEntityToDomainModel(weatherEntityModel))
            } catch (error: Exception) {
                _uiState.value = UiState.Error(error.toString())
            }
        }
    }

    suspend fun updateWeatherData(weatherEntityModel: WeatherEntityModel) =
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepositoryImpl.deleteAllWeather()
            weatherRepositoryImpl.insertWeather(weatherEntityModel)
        }

    fun getSavedWeather() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        weatherRepositoryImpl.getSavedWeather().flowOn(Dispatchers.IO)
            .catch { exception ->
                _uiState.value = UiState.Error(exception.toString())
            }.collect {
                val weatherDomainModel = modelConverter.convertEntityToDomainModel(it)
                //onResource(Resource.success(weatherDomainModel))
                _uiState.value = UiState.Success(weatherDomainModel)
            }
    }

    /*   private fun <T> Flow<T>.catchError(): Flow<T> {
           return catch {
               onResource(Resource.error())
           }
       }*/

}