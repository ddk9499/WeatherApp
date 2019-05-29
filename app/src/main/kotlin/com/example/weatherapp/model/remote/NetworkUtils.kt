package com.example.weatherapp.model.remote

import android.content.Context
import android.net.ConnectivityManager

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

class NetworkUtils(context: Context) {

	private val connectivityManager by lazy { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

	fun isOnline(): Boolean {
		val netInfo = connectivityManager.activeNetworkInfo
		return netInfo != null && netInfo.isConnected
	}
}