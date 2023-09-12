package com.weather.search.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.weather.search.ui.theme.WeatherSearchTheme
import com.weather.search.view.screens.DetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {

    private val item by lazy { intent.getIntExtra(ITEM_KEY, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DetailScreen(item)
                }
            }
        }
    }

    companion object {
        private const val ITEM_KEY = "KEY"
        fun newIntent(context: Context, item: Int) =
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(ITEM_KEY, item)
            }
    }
}