package com.weather.search.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weather.search.viewmodel.WeatherListViewModel

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun WeatherSearchScreen(viewModelWeather: WeatherListViewModel = viewModel()) {
    Scaffold(
        topBar = { SearchAppBar(viewModel = viewModelWeather) }
    ) {
        val isLoadingContentState by viewModelWeather.isLoadingContentState.collectAsStateWithLifecycle()
        val isErrorContentState by viewModelWeather.isErrorContentState.collectAsStateWithLifecycle()
        val successContentState by viewModelWeather.successContentState.collectAsStateWithLifecycle()

        if (isLoadingContentState) {
            LoadingScreen()
        }
        successContentState?.let { SuccessScreen(it) }
        if (isErrorContentState) {
            ErrorScreen()
        }
    }
}