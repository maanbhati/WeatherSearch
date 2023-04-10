package com.weather.search.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.search.databinding.ActivityWeatherSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityWeatherSearchBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }
}

