package com.example.weatherforecasts.ui.daysForecastScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecasts.domain.UpdateWeatherController
import com.example.weatherforecasts.ui.models.DaysForecastModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaysForecastViewModel @Inject constructor(
    private val controller: UpdateWeatherController
) : ViewModel() {

    private var _daysForecast = MutableLiveData<List<DaysForecastModel>>()
    val daysForecast get() = _daysForecast

    init {
        viewModelScope.launch {
            controller.listenDaysWeather().collect {
                _daysForecast.postValue(it)
            }
        }
    }
}