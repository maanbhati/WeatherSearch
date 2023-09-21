package com.weather.search.view.screens

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weather.search.core.UiState
import com.weather.search.data.remote.WeatherDomainViewModel
import com.weather.search.viewmodel.WeatherListViewModel

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun WeatherSearchScreen(viewModelWeather: WeatherListViewModel = viewModel()) {
    Scaffold(
        topBar = { SearchAppBar(viewModel = viewModelWeather) }
    ) {
        val uiState by viewModelWeather.uiState.collectAsStateWithLifecycle()
        when (uiState) {
            is UiState.Loading -> LoadingScreen()

            is UiState.Success -> {
                (uiState as UiState.Success<WeatherDomainViewModel?>).data?.let {
                    SuccessScreen(it)
                }
            }

            is UiState.Error -> ErrorScreen()
        }
    }
}