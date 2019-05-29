package com.example.weatherapp.model.remote

import com.example.weatherapp.entities.WeatherData
import com.example.weatherapp.entities.WeatherForGroup
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

interface WeatherApi {

	@GET("group")
	fun currentWeatherForGroup(@QueryMap options: Map<String, String>): Deferred<WeatherForGroup>

	@GET("weather")
	fun currentWeather(@QueryMap options: Map<String, String>): Deferred<WeatherData>

}