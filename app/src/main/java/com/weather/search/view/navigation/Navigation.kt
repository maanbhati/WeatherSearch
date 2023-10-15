package com.weather.search.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.weather.search.data.remote.WeatherDomainViewModel
import com.weather.search.utils.LIST_SCREEN
import com.weather.search.view.screens.Screens
import com.weather.search.view.screens.detailComposable
import com.weather.search.view.screens.listComposable

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    successContentState: WeatherDomainViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(
            navigateToDetailScreen = screen.list,
            successContentState = successContentState
        )
        detailComposable(
            navigationToListScreen = screen.details
        )
    }
}
