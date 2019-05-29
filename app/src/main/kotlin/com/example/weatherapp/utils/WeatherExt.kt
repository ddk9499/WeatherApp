package com.example.weatherapp.utils

import android.content.Context
import com.example.weatherapp.R
import com.example.weatherapp.entities.Direction
import com.example.weatherapp.entities.Temperature
import com.example.weatherapp.entities.Wind

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

fun Wind.toDirection(): Direction =
	when (this.degree.substringBefore('.').toInt()) {
		in 0..22, in 337..360 -> Direction(R.string.azimuth_n, R.drawable.ic_azimuth_n)
		in 23..67 -> Direction(R.string.azimuth_ne, R.drawable.ic_azimuth_ne)
		in 68..112 -> Direction(R.string.azimuth_e, R.drawable.ic_azimuth_e)
		in 113..157 -> Direction(R.string.azimuth_se, R.drawable.ic_azimuth_se)
		in 158..202 -> Direction(R.string.azimuth_s, R.drawable.ic_azimuth_s)
		in 203..247 -> Direction(R.string.azimuth_sw, R.drawable.ic_azimuth_sw)
		in 248..292 -> Direction(R.string.azimuth_w, R.drawable.ic_azimuth_w)
		in 293..336 -> Direction(R.string.azimuth_nw, R.drawable.ic_azimuth_nw)
		else -> throw IllegalArgumentException()
	}

fun Temperature.toCelsius(context: Context) =
	"${this.temperature.substringBefore('.')}${context.getString(R.string.celsius_sign)}"