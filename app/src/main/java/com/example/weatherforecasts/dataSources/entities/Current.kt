package com.example.weatherforecasts.dataSources.entities

data class Current(
    val condition: Condition,
    val last_updated: String,
    val temp_c: Double
)