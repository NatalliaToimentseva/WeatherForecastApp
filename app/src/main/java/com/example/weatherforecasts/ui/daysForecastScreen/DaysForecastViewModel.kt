package com.example.weatherforecasts.ui.daysForecastScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecasts.dataSources.VolleyLoader
import com.example.weatherforecasts.models.DaysForecastModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DaysForecastViewModel @Inject constructor(): ViewModel() {

    private val loader = VolleyLoader()

    private var _daysForecast = MutableLiveData<List<DaysForecastModel>>()
    val daysForecast get() = _daysForecast

    fun getDaysForecast(weather: String) {
        _daysForecast.value = loader.parseDaysForecast(weather)
    }
}