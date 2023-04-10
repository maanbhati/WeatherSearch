package com.weather.search.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.search.utils.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM $TABLE_NAME")
    fun getSavedWeather(): Flow<WeatherEntityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherEntityModel: WeatherEntityModel): Long

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()
}