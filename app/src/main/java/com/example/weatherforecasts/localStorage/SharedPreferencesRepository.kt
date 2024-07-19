package com.example.weatherforecasts.localStorage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_pref"
private const val WEATHER_REMOTE_DATA = "weather model"

@Singleton
class SharedPreferencesRepository @Inject constructor(@ApplicationContext context: Context) {

    private val userPreferences: SharedPreferences

    init {
        userPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun setWeatherData(data: String) {
        userPreferences.edit { putString(WEATHER_REMOTE_DATA, data) }
    }

    fun getWeatherData(): String? {
        return userPreferences.getString(WEATHER_REMOTE_DATA, null)
    }

    fun clearUserData() {
        userPreferences.edit {
            clear()
        }
    }
}