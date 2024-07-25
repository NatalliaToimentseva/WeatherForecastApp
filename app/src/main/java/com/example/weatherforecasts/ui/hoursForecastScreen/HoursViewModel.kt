package com.example.weatherforecasts.ui.hoursForecastScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecasts.domain.UpdateWeatherController
import com.example.weatherforecasts.ui.models.HoursForecastModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoursViewModel @Inject constructor(
    private val controller: UpdateWeatherController
) : ViewModel() {

    private var _hoursForecast = MutableLiveData<List<HoursForecastModel>>()
    val hoursForecast get() = _hoursForecast

    init {
        viewModelScope.launch {
            controller.listenHoursWeather().collect {
                _hoursForecast.postValue(it)
            }
        }
    }
}