package com.example.weatherapp.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

@GlideModule
class GlideModule : AppGlideModule() {

	override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
		val client = OkHttpClient.Builder()
			.cache(Cache(context.cacheDir, CACHE_SIZE_BYTES))
			.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
			.readTimeout(TIMEOUT, TimeUnit.SECONDS)
			.build()

		val factory = OkHttpUrlLoader.Factory(client)
		glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
	}

	override fun isManifestParsingEnabled() = false

	companion object {
		private const val CACHE_SIZE_BYTES = 20 * 1024L
		private const val TIMEOUT = 30L
	}
}