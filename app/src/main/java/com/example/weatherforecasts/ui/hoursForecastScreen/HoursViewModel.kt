package com.example.weatherforecasts.ui.hoursForecastScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecasts.dataSources.VolleyLoader
import com.example.weatherforecasts.models.HoursForecastModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HoursViewModel @Inject constructor(): ViewModel() {

    private val loader = VolleyLoader()


    private var _hoursForecast = MutableLiveData<List<HoursForecastModel>>()
    val hoursForecast get() = _hoursForecast

    fun getHoursForecast(weather: String) {
        _hoursForecast.value = loader.parseHoursWeather(weather)
    }
}