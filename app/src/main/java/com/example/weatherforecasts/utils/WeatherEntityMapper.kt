package com.example.weatherforecasts.utils

import com.example.weatherforecasts.dataSources.entities.WeatherEntity
import com.example.weatherforecasts.ui.models.CurrentDayModel
import com.example.weatherforecasts.ui.models.DaysForecastModel
import com.example.weatherforecasts.ui.models.HoursForecastModel

fun WeatherEntity.toCurrentDayModel(): CurrentDayModel {
    return CurrentDayModel(
        city = this.location.name,
        dateTime = this.current.last_updated,
        condition = this.current.condition.text,
        currentTemp = this.current.temp_c.toInt().toString(),
        maxTemp = this.forecast.forecastday[0].day.maxtemp_c.toInt().toString(),
        mintTemp = this.forecast.forecastday[0].day.mintemp_c.toInt().toString(),
        imageUrl = this.current.condition.icon
    )
}

fun WeatherEntity.toListHoursForecastModel(): List<HoursForecastModel> {
    val hoursList = ArrayList<HoursForecastModel>()
    val currentDayHours = this.forecast.forecastday[0].hour
    for (hour in currentDayHours) {
        hoursList.add(
            HoursForecastModel(
                time = hour.time,
                condition = hour.condition.text,
                currentTemp = hour.temp_c.toInt().toString(),
                imageUrl = hour.condition.icon
            )
        )
    }
    return hoursList
}

fun WeatherEntity.toListDaysForecastModel(): List<DaysForecastModel> {
    val daysList = ArrayList<DaysForecastModel>()
    val days = this.forecast.forecastday
    for (day in days) {
        daysList.add(
            DaysForecastModel(
                date = day.date,
                condition = day.day.condition.text,
                maxTemp = day.day.maxtemp_c.toInt().toString(),
                mintTemp = day.day.mintemp_c.toInt().toString(),
                imageUrl = day.day.condition.icon
            )
        )
    }
    return daysList
}