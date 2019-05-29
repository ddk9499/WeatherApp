package com.example.weatherapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

data class WeatherForGroup(
	@SerializedName("list")
	val weathers: List<WeatherData>
)

data class WeatherData(
	@SerializedName("name")
	val cityName: String,
	@SerializedName("weather")
	val weather: List<Weather>,
	@SerializedName("main")
	val temperature: Temperature,
	@SerializedName("wind")
	val wind: Wind
) : Serializable

data class Weather(
	@SerializedName("icon")
	val icon: String,
	@SerializedName("description")
	val description: String
)

data class Temperature(
	@SerializedName("temp")
	val temperature: String,
	@SerializedName("humidity")
	val humidity: String,
	@SerializedName("pressure")
	val pressure: String,
	@SerializedName("temp_min")
	val minTemperature: String,
	@SerializedName("temp_max")
	val maxTemperature: String
)

data class Wind(
	@SerializedName("speed")
	val speed: String,
	@SerializedName("deg")
	val degree: String
)
