package com.weather.search.view.screens

import androidx.navigation.NavHostController
import com.weather.search.utils.LIST_SCREEN
import com.weather.search.view.navigation.Action

class Screens(navController: NavHostController) {
    val list: (String) -> Unit = { weatherDetail ->
        navController.navigate(route = "details/${weatherDetail}")
    }

    val details: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
}