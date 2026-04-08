package ru.tiredcat.weatherapp.utils

import android.content.Context

class SettingsManager(context: Context){
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveLastQuery(city: String){
        sharedPreferences.edit().putString(KEY_LAST_QUERY, city).apply()
    }

    fun getLastQuery(): String{
        return sharedPreferences.getString(KEY_LAST_QUERY, DEFAULT_CITY) ?: DEFAULT_CITY
    }



    companion object {
        private const val KEY_LAST_QUERY = "last_query"
        private const val DEFAULT_CITY = "Moscow"
    }
}