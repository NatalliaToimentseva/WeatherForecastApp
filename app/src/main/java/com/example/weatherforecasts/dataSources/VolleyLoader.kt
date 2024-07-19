package com.example.weatherforecasts.dataSources

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherforecasts.constants.DAYS
import com.example.weatherforecasts.constants.KEY
import com.example.weatherforecasts.models.WeatherModel
import org.json.JSONArray
import org.json.JSONObject

class VolleyLoader {

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

    fun parseWeatherData(
        result: String,
        getCurrent: (item: WeatherModel) -> Unit,
        getDays: (List<WeatherModel>) -> Unit
    ) {
        val mainObject = JSONObject(result)
        val list = parseDays(mainObject)
        getCurrent(parseCurrentData(mainObject, list[0]))
        getDays(list)
    }

    fun getHoursWeather(item: WeatherModel): List<WeatherModel> {
        val hoursArr = JSONArray(item.hours)
        val hoursList = ArrayList<WeatherModel>()
        for (i in 0 until hoursArr.length()) {
            val hItem = WeatherModel(
                item.city,
                (hoursArr[i] as JSONObject).getString("time"),
                (hoursArr[i] as JSONObject).getJSONObject("condition").getString("text"),
                (hoursArr[i] as JSONObject).getString("temp_c"),
                "", "",
                (hoursArr[i] as JSONObject).getJSONObject("condition").getString("icon"),
                ""
            )
            hoursList.add(hItem)
        }
        return hoursList
    }

    private fun parseDays(mainObject: JSONObject): List<WeatherModel> {
        val list = ArrayList<WeatherModel>()
        val daysArr = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        val name = mainObject.getJSONObject("location").getString("name")
        for (i in 0 until daysArr.length()) {
            val day = daysArr[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c"),
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        return list
    }

    private fun parseCurrentData(mainObject: JSONObject, weatherItem: WeatherModel): WeatherModel {
        return WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weatherItem.maxTemp,
            weatherItem.mintTemp,
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
            weatherItem.hours
        )
    }
}