package com.example.weatherforecasts.dataSources

import com.example.weatherforecasts.LoadDataException
import com.example.weatherforecasts.constants.DAYS
import com.example.weatherforecasts.constants.KEY
import com.example.weatherforecasts.domain.UpdateWeatherController
import com.example.weatherforecasts.repository.WeatherDataRepository
import com.example.weatherforecasts.utils.toCurrentDayModel
import com.example.weatherforecasts.utils.toListDaysForecastModel
import com.example.weatherforecasts.utils.toListHoursForecastModel
import javax.inject.Inject

class RetrofitWeatherRepository @Inject constructor(
    private val api: RetrofitApi,
    private val controller: UpdateWeatherController
) : WeatherDataRepository {

    override suspend fun loadWeatherDate(location: String) {
        val result = api.getWeatherData(KEY, location, DAYS)
        if (result.isSuccessful) {
            result.body()?.toCurrentDayModel()?.let { controller.updatedCurrentWeather.tryEmit(it) }
            result.body()?.toListHoursForecastModel()
                ?.let { controller.updatedHoursWeather.tryEmit(it) }
            result.body()?.toListDaysForecastModel()
                ?.let { controller.updatedDaysWeather.tryEmit(it) }
        } else {
            throw LoadDataException(result.code().toString() + ": " + result.errorBody()?.string())
        }
    }
}