package com.example.weatherapp.di

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.remote.AuthInterceptor
import com.example.weatherapp.model.remote.CoroutineCallAdapterFactory
import com.example.weatherapp.model.remote.NetworkUtils
import com.example.weatherapp.model.remote.WeatherApi
import com.example.weatherapp.model.storage.Prefs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */


val myModule = module {
	single { createOkHttpClient(getProperty("secret_key")) }
	single { createWebService<WeatherApi>(get(), getProperty("server_url")) }
	single { Prefs }
	single { NetworkUtils(get()) }
}

private fun createOkHttpClient(secretKey: String): OkHttpClient {
	val loggingInterceptor = HttpLoggingInterceptor()
	loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

	return with(OkHttpClient.Builder()) {
		connectTimeout(30L, TimeUnit.SECONDS)
		readTimeout(30L, TimeUnit.SECONDS)
		addInterceptor(AuthInterceptor(secretKey))
		if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
		build()
	}
}

private inline fun <reified T> createWebService(client: OkHttpClient, url: String): T {
	val retrofit = Retrofit.Builder()
		.baseUrl(url)
		.client(client)
		.addCallAdapterFactory(CoroutineCallAdapterFactory())
		.addConverterFactory(GsonConverterFactory.create())
		.build()
	return retrofit.create(T::class.java)
}