package com.weather.search.view.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.weather.search.utils.DETAIL_ARGUMENT_KEY
import com.weather.search.utils.DETAIL_SCREEN
import com.weather.search.view.navigation.Action

@ExperimentalAnimationApi
fun NavGraphBuilder.detailComposable(
    navigationToListScreen: (Action) -> Unit
) {
    composable(
        route = DETAIL_SCREEN,
        arguments = listOf(navArgument(DETAIL_ARGUMENT_KEY) { type = NavType.StringType })
    ) { navBackStackEntry ->
        val weatherDetails = navBackStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY)
        weatherDetails?.let {
            DetailScreen(
                onClick = { navigationToListScreen(Action.TO_LIST_SCREEN) },
                item = weatherDetails
            )
        }
    }
}