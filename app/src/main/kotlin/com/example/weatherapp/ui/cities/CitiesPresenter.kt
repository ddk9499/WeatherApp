package com.example.weatherapp.ui.cities

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.weatherapp.model.remote.WeatherApi
import com.example.weatherapp.model.storage.Prefs
import kotlinx.coroutines.*
import java.net.UnknownHostException

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

@InjectViewState
class CitiesPresenter(
	private val api: WeatherApi,
	private val prefs: Prefs
) : MvpPresenter<CitiesView>(), CoroutineScope {

	private val job = SupervisorJob()
	override val coroutineContext = job + Dispatchers.Main

	override fun onFirstViewAttach() {
		loadWeatherData()
	}

	fun loadWeatherData() = launch {
		try {
			viewState.showProgress(true)
			val cities = prefs.cities.joinToString(",")
			val params = mapOf("units" to "metric", "id" to cities)
			val data = withContext(Dispatchers.IO) { api.currentWeatherForGroup(params) }.await()
			viewState.showWeathersTable(data.weathers)
		} catch (e: UnknownHostException) {
			viewState.noInternetConnection()
		} catch (e: Exception) {
			Log.e("CitiesPresenter", "loadWeatherData: ", e)
		} finally {
			viewState.showProgress(false)
		}
	}

	fun addCity(cityCode: String) {
		launch {
			try {
				withContext(Dispatchers.IO) { api.currentWeather(mapOf("id" to cityCode)) }.await()
				prefs.cities += cityCode
				loadWeatherData()
				viewState.hideDialog()
			} catch (e: Exception) {
				viewState.wrongCode()
			}
		}
	}

	override fun onDestroy() {
		coroutineContext.cancelChildren()
	}
}