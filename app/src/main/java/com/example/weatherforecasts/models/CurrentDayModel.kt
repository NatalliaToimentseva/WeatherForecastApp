package com.example.weatherforecasts.models

sealed class Weather

data class CurrentDayModel(
    val city: String,
    val dateTime: String,
    val condition: String,
    val currentTemp:String,
    val maxTemp:String,
    val mintTemp:String,
    val imageUrl: String,
) : Weather()


data class DaysForecastModel(
    val date: String,
    val condition: String,
    val maxTemp:String,
    val mintTemp:String,
    val imageUrl: String
) : Weather()

data class HoursForecastModel(
    val time: String,
    val condition: String,
    val currentTemp:String,
    val imageUrl: String
) : Weather()