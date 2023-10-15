package com.weather.search.view.navigation

enum class Action {
    TO_LIST_SCREEN,
    FROM_LIST_SCREEN,
    NO_ACTION
}

fun String?.toAction(): Action {
    return if (this.isNullOrEmpty()) Action.NO_ACTION else Action.valueOf(this)
}