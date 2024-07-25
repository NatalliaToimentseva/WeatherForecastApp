package com.example.weatherforecasts.dataSources.entities

data class Day(
    val condition: Condition,
    val maxtemp_c: Double,
    val mintemp_c: Double
)