package com.example.weatherforecasts.dataSources

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherforecasts.constants.DAYS
import com.example.weatherforecasts.constants.KEY
import com.example.weatherforecasts.models.CurrentDayModel
import com.example.weatherforecasts.models.DaysForecastModel
import com.example.weatherforecasts.models.HoursForecastModel
import org.json.JSONObject
import javax.inject.Inject

class VolleyLoader @Inject constructor() {

    fun requestWeatherData(context: Context, city: String, loadData: (result: String) -> Unit) {
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=$KEY&q=$city&days=$DAYS&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                loadData(result)
                Log.d("AAA", "Loaded: $result")
            },
            { error ->
                throw LoadDataException(error.message)
            }
        )
        queue.add(request)
    }

    fun parseHoursWeather(result: String): List<HoursForecastModel> {
        val hoursList = ArrayList<HoursForecastModel>()
        val currentDayObj = getCurrentDayObj(result)
        val hoursArr = currentDayObj.getJSONArray("hours")

        for (i in 0 until hoursArr.length()) {
            val hItem = HoursForecastModel(
                (hoursArr[i] as JSONObject).getString("time"),
                (hoursArr[i] as JSONObject).getJSONObject("condition").getString("text"),
                (hoursArr[i] as JSONObject).getString("temp_c"),
                (hoursArr[i] as JSONObject).getJSONObject("condition").getString("icon"),
            )
            hoursList.add(hItem)
        }
        return hoursList
    }

    fun parseDaysForecast(result: String): List<DaysForecastModel> {
        val daysList = ArrayList<DaysForecastModel>()
        val mainObject = JSONObject(result)
        val daysArr = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        for (i in 0 until daysArr.length()) {
            val day = daysArr[i] as JSONObject
            val item = DaysForecastModel(
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                day.getJSONObject("day").getString("maxtemp_c"),
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONObject("day").getJSONObject("condition").getString("icon")
            )
            daysList.add(item)
        }
        return daysList
    }

    fun parseCurrentData(result: String): CurrentDayModel {
        val mainObject = JSONObject(result)
        val currentDayObj = getCurrentDayObj(result)
        return CurrentDayModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            currentDayObj.getJSONObject("day").getString("maxtemp_c"),
            currentDayObj.getJSONObject("day").getString("mintemp_c"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
        )
    }

    private fun getCurrentDayObj(result: String): JSONObject {
        val mainObject = JSONObject(result)
        val daysArr = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        return daysArr[0] as JSONObject
    }
}