package com.example.weatherforecasts.dataSources.entities

data class Forecastday(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)