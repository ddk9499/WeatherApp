package com.example.weatherapp.model.storage

import com.chibatching.kotpref.KotprefModel
import java.util.*

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

object Prefs : KotprefModel() {

	val cities by stringSetPref {
		val set = TreeSet<String>().apply {
			add("524901") // Moscow
			add("1512569") // Tashkent
			add("2643743") // London
			add("1850147") // Tokyo
		}
		return@stringSetPref set
	}

}