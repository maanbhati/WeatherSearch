package com.weather.search.view.screens

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.weather.search.core.UiState
import com.weather.search.data.remote.WeatherDomainViewModel
import com.weather.search.view.navigation.SetupNavigation
import com.weather.search.viewmodel.WeatherListViewModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun WeatherSearchScreen(
    navController: NavHostController,
    viewModelWeather: WeatherListViewModel = viewModel()
) {
    Scaffold(
        topBar = { SearchAppBar(viewModel = viewModelWeather) }
    ) {
        val uiState by viewModelWeather.uiState.collectAsStateWithLifecycle()
        when (uiState) {
            is UiState.Loading -> LoadingScreen()

            is UiState.Success -> {
                (uiState as UiState.Success<WeatherDomainViewModel?>).data?.let {
                    SetupNavigation(navController = navController, successContentState = it)
                }
            }

            is UiState.Error -> ErrorScreen()
        }
    }
}