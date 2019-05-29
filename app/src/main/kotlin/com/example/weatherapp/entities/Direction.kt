package com.example.weatherapp.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

data class Direction(@StringRes val title: Int, @DrawableRes val icon: Int)