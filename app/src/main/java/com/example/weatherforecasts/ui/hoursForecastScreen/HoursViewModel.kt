package com.example.weatherforecasts.ui.hoursForecastScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecasts.domain.UpdateWeatherController
import com.example.weatherforecasts.models.HoursForecastModel
import com.example.weatherforecasts.repository.WeatherDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoursViewModel @Inject constructor(
    private val repository: WeatherDataRepository
) : ViewModel() {

    private var _hoursForecast = MutableLiveData<List<HoursForecastModel>>()
    val hoursForecast get() = _hoursForecast

    init {
        viewModelScope.launch {
            UpdateWeatherController.listenWeatherUpdating().collect {
                getHoursForecast(it)
            }
        }
    }

    fun getHoursForecast(weather: String) {
        _hoursForecast.value = repository.getHoursForecast(weather)
    }
}