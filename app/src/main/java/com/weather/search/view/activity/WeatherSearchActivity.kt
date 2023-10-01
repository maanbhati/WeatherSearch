package com.weather.search.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.weather.search.ui.theme.WeatherSearchTheme
import com.weather.search.view.screens.WeatherSearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherSearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WeatherSearchScreen()
                }
            }
        }
    }
}

