package com.example.weatherforecasts.repository

import com.example.weatherforecasts.LoadDataException
import com.example.weatherforecasts.constants.DAYS
import com.example.weatherforecasts.constants.DIVIDER
import com.example.weatherforecasts.constants.KEY
import com.example.weatherforecasts.domain.UpdateWeatherController
import com.example.weatherforecasts.network.RetrofitApi
import com.example.weatherforecasts.utils.toCurrentDayModel
import com.example.weatherforecasts.utils.toListDaysForecastModel
import com.example.weatherforecasts.utils.toListHoursForecastModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface WeatherDataRepository {

    suspend fun loadWeatherDate(location: String)
}

class RetrofitWeatherRepository @Inject constructor(
    private val api: RetrofitApi,
    private val controller: UpdateWeatherController
) : WeatherDataRepository {

    override suspend fun loadWeatherDate(location: String) {
        try {
            val result = api.getWeatherData(KEY, location, DAYS)
            if (result.isSuccessful) {
                result.body()?.let { weatherResponse ->
                    controller.updatedCurrentWeather.tryEmit(weatherResponse.toCurrentDayModel())
                    controller.updatedHoursWeather.tryEmit(weatherResponse.toListHoursForecastModel())
                    controller.updatedDaysWeather.tryEmit(weatherResponse.toListDaysForecastModel())
                }
            } else {
                throw LoadDataException(
                    result.code().toString() + DIVIDER + result.errorBody()?.string()
                )
            }
        } catch (e: IOException) {
            throw LoadDataException(e.message)
        } catch (e: HttpException) {
            throw LoadDataException(e.message)
        }
    }
}