package com.example.weatherapp.ui.cities

import com.arellomobile.mvp.MvpView
import com.example.weatherapp.entities.WeatherData

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

interface CitiesView : MvpView {
	fun showProgress(isVisible: Boolean)
	fun noInternetConnection()
	fun wrongCode()
	fun hideDialog()
	fun showWeathersTable(items: List<WeatherData>)
}