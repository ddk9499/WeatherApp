package com.example.weatherapp.model.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

class AuthInterceptor(private val secretKey: String) : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val url = chain.request().url().newBuilder()
			.addQueryParameter("appid", secretKey)
			.build()

		val request = chain.request().newBuilder()
			.url(url)
			.build()

		return chain.proceed(request)
	}
}