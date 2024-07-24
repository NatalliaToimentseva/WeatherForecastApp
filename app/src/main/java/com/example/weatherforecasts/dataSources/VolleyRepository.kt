package com.example.weatherforecasts.dataSources

import android.content.Context
import com.example.weatherforecasts.models.CurrentDayModel
import com.example.weatherforecasts.models.DaysForecastModel
import com.example.weatherforecasts.models.HoursForecastModel
import com.example.weatherforecasts.repository.WeatherDataRepository
import javax.inject.Inject

class VolleyRepository @Inject constructor(
    private val loader: VolleyLoader
) : WeatherDataRepository {

    override suspend fun loadWeatherDate(context: Context, location: String): String {
        return loader.requestWeatherData(context, location)
    }

    override fun getCurrentDayWeather(data: String): CurrentDayModel {
        return loader.parseCurrentData(data)
    }

    override fun getDaysForecast(data: String): List<DaysForecastModel> {
        return loader.parseDaysForecast(data)
    }

    override fun getHoursForecast(data: String): List<HoursForecastModel> {
        return loader.parseHoursWeather(data)
    }
}