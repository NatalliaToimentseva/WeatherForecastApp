package com.example.weatherforecasts.ui.homeScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecasts.dataSources.LoadDataException
import com.example.weatherforecasts.dataSources.VolleyLoader
import com.example.weatherforecasts.models.WeatherModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val loader = VolleyLoader()

    private var _currentDayWeather = MutableLiveData<WeatherModel>()
    val currentDayWeather get() = _currentDayWeather

    private var _daysForecast = MutableLiveData<List<WeatherModel>>()
    val daysForecast get() = _daysForecast

    private var _hoursForecast = MutableLiveData<List<WeatherModel>>()
    val hoursForecast get() = _hoursForecast

    private val weatherData = MutableLiveData<String?>(null)
    private val _errorsGettingData = MutableLiveData<String?>(null)
    val errorsGettingData get() = _errorsGettingData

    fun clearError() {
        _errorsGettingData.value = null
    }
    fun setError(message: String) {
        _errorsGettingData.value = message
    }

    fun getWeatherData(context: Context, city: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            loader.requestWeatherData(context, city) { result ->
                Log.d("AAA", "get result in lambda: $result")
                weatherData.value = result
                getParsedWeather()
                Log.d("AAA", "Viewmodel get data: ${weatherData.value}")
            }
        } catch (e: LoadDataException) {
            _errorsGettingData.postValue(e.message)
        }
    }

    fun getHoursForecast() {
        _currentDayWeather.value?.let {
            _hoursForecast.value = loader.getHoursWeather(it)
        }
    }

    private fun getParsedWeather() {
        Log.d("AAA", "data for parsing from viewmodel: ${weatherData.value}")
        weatherData.value?.let { weatherData ->
            loader.parseWeatherData(weatherData,
                { currentWeather ->
                    _currentDayWeather.postValue(currentWeather)
                    Log.d("AAA", "Viewmodel get parsed data for current day: $currentWeather")
                },
                { daysWeather ->
                    _daysForecast.postValue(daysWeather)
                    Log.d("AAA", "Viewmodel get parsed data for list days: $daysWeather")
                })
        }
    }
}