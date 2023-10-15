package com.weather.search.view.screens

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.weather.search.data.remote.WeatherDomainViewModel
import com.weather.search.utils.LIST_ARGUMENT_KEY
import com.weather.search.utils.LIST_SCREEN
import com.weather.search.view.navigation.Action
import com.weather.search.view.navigation.toAction

fun NavGraphBuilder.listComposable(
    navigateToDetailScreen: (weatherDetail: String) -> Unit,
    successContentState: WeatherDomainViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) { type = NavType.StringType })
    )
    { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        var myAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }
        LaunchedEffect(key1 = myAction) {
            if (action != myAction) {
                myAction = action
            }
        }
        SuccessScreen(
            navigateToDetailScreen,
            successContentState
        )
    }
}