package com.example.weatherforecasts.ui.homeScreen

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecasts.LoadDataException
import com.example.weatherforecasts.domain.UpdateWeatherController
import com.example.weatherforecasts.ui.models.CurrentDayModel
import com.example.weatherforecasts.repository.WeatherDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherDataRepository,
    private val controller: UpdateWeatherController
) : ViewModel() {

    private var _currentDayWeather = MutableLiveData<CurrentDayModel>()
    val currentDayWeather get() = _currentDayWeather

    private val _errorsGettingData = MutableLiveData<String?>(null)
    val errorsGettingData get() = _errorsGettingData

    private val _isInProgress = MutableLiveData(false)
    val isInProgress get() = _isInProgress

    init {
        viewModelScope.launch {
            controller.listenCurrentWeather().collect {
                _currentDayWeather.postValue(it)
            }
        }
    }

    fun clearError() {
        _errorsGettingData.value = null
    }

    fun setError(message: String) {
        _errorsGettingData.value = message
    }

    fun getWeatherData(context: Context, city: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _isInProgress.postValue(true)
            repository.loadWeatherDate(city)
            _isInProgress.postValue(false)
        } catch (e: LoadDataException) {
            _errorsGettingData.postValue(e.message)
            _isInProgress.postValue(false)
        }
    }
}