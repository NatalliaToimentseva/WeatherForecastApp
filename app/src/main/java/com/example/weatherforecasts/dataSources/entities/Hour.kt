package com.example.weatherforecasts.dataSources.entities

data class Hour(
    val condition: Condition,
    val temp_c: Double,
    val time: String,
)