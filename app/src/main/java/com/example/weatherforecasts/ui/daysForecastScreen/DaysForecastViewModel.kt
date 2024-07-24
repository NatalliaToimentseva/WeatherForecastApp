package com.example.weatherforecasts.ui.daysForecastScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecasts.domain.UpdateWeatherController
import com.example.weatherforecasts.models.DaysForecastModel
import com.example.weatherforecasts.repository.WeatherDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaysForecastViewModel @Inject constructor(
    private val repository: WeatherDataRepository
) : ViewModel() {

    private var _daysForecast = MutableLiveData<List<DaysForecastModel>>()
    val daysForecast get() = _daysForecast

    init {
        viewModelScope.launch {
            UpdateWeatherController.listenWeatherUpdating().collect {
                getDaysForecast(it)
            }
        }
    }

    fun getDaysForecast(weather: String) {
        _daysForecast.value = repository.getDaysForecast(weather)
    }
}