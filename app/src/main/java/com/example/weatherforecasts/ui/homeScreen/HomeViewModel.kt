package com.example.weatherforecasts.ui.homeScreen

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecasts.dataSources.LoadDataException
import com.example.weatherforecasts.dataSources.VolleyLoader
import com.example.weatherforecasts.models.CurrentDayModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val loader = VolleyLoader()

    private var _currentDayWeather = MutableLiveData<CurrentDayModel>()
    val currentDayWeather get() = _currentDayWeather

    private val _weatherData = MutableLiveData<String?>(null)
    val weatherData get() = _weatherData

    private val _errorsGettingData = MutableLiveData<String?>(null)
    val errorsGettingData get() = _errorsGettingData

    private val _isInProgress = MutableLiveData(false)
    val isInProgress get() = _isInProgress

    fun clearError() {
        _errorsGettingData.value = null
    }

    fun setError(message: String) {
        _errorsGettingData.value = message
    }

    fun getWeatherData(context: Context, city: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _isInProgress.postValue(true)
            loader.requestWeatherData(context, city) { result ->
                weatherData.value = result
            }
            delay(1000)
            _isInProgress.postValue(false)
        } catch (e: LoadDataException) {
            _errorsGettingData.postValue(e.message)
        }
    }

    fun getParsedCurrentDayWeather(weather: String) {
        _currentDayWeather.value = loader.parseCurrentData(weather)
    }
}