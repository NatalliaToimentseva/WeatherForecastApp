package com.example.weatherforecasts.dataSources

import android.content.Context
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
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class VolleyLoader @Inject constructor() {

    suspend fun requestWeatherData(context: Context, city: String): String {
        return suspendCoroutine { continuation ->
            val url =
                "https://api.weatherapi.com/v1/forecast.json?key=$KEY&q=$city&days=$DAYS&aqi=no&alerts=no"
            val queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET,
                url,
                { result ->
                    continuation.resume(result)
                },
                { error ->
                    continuation.resumeWithException(LoadDataException(error.message))
                }
            )
            queue.add(request)
        }
    }

    fun parseHoursWeather(result: String): List<HoursForecastModel> {
        val hoursList = ArrayList<HoursForecastModel>()
        val currentDayObj = getCurrentDayObj(result)
        val hoursArr = currentDayObj.getJSONArray("hour")
        for (i in 0 until hoursArr.length()) {
            val hItem = HoursForecastModel(
                (hoursArr[i] as JSONObject).getString("time"),
                (hoursArr[i] as JSONObject).getJSONObject("condition").getString("text"),
                (hoursArr[i] as JSONObject).getString("temp_c").toFloat().toInt().toString(),
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
                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
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
            mainObject.getJSONObject("current").getString("temp_c").toFloat().toInt().toString(),
            currentDayObj.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
            currentDayObj.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
        )
    }

    private fun getCurrentDayObj(result: String): JSONObject {
        val mainObject = JSONObject(result)
        val daysArr = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        return daysArr[0] as JSONObject
    }
}