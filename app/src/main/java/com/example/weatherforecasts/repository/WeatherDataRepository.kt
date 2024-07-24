package com.example.weatherforecasts.repository

import android.content.Context
import com.example.weatherforecasts.models.CurrentDayModel
import com.example.weatherforecasts.models.DaysForecastModel
import com.example.weatherforecasts.models.HoursForecastModel

interface WeatherDataRepository {

    suspend fun loadWeatherDate(context: Context, location: String): String?

    fun getCurrentDayWeather(data: String): CurrentDayModel

    fun getDaysForecast(data: String): List<DaysForecastModel>

    fun getHoursForecast(data: String): List<HoursForecastModel>
}