package com.example.weatherapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.R

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

var View.visible: Boolean
	get() = visibility == View.VISIBLE
	set(value) {
		visibility = if (value) View.VISIBLE else View.GONE
	}

fun ImageView.loadWeatherIcon(icon: String) {
	Glide.with(context)
		.load("https://openweathermap.org/img/w/$icon.png")
		.apply(RequestOptions().apply {
			placeholder(R.drawable.ic_weather_error)
			error(R.drawable.ic_weather_error)
		})
		.into(this)
}

fun ViewGroup.inflate(@LayoutRes layout: Int): View =
	LayoutInflater.from(context).inflate(layout, this, false)