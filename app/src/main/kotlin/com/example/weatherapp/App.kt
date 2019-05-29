package com.example.weatherapp

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.example.weatherapp.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

class App : Application() {

	override fun onCreate() {
		super.onCreate()

		Kotpref.init(this)

		startKoin {
			androidLogger()
			androidContext(this@App)
			androidFileProperties()
			loadKoinModules(myModule)
		}
	}
}