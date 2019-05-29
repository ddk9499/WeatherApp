package com.example.weatherapp.model.remote

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

class CoroutineCallAdapterFactory private constructor() : CallAdapter.Factory() {
	companion object {
		@JvmStatic
		@JvmName("create")
		operator fun invoke() = CoroutineCallAdapterFactory()
	}

	override fun get(
		returnType: Type,
		annotations: Array<out Annotation>,
		retrofit: Retrofit
	): CallAdapter<*, *>? {
		if (Deferred::class.java != getRawType(returnType)) {
			return null
		}
		if (returnType !is ParameterizedType) {
			throw IllegalStateException(
				"Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>"
			)
		}
		val responseType = getParameterUpperBound(0, returnType)

		return BodyCallAdapter<Any>(responseType)
	}

	private class BodyCallAdapter<T>(
		private val responseType: Type
	) : CallAdapter<T, Deferred<T>> {

		override fun responseType() = responseType

		override fun adapt(call: Call<T>): Deferred<T> {
			val deferred = CompletableDeferred<T>()

			deferred.invokeOnCompletion {
				if (deferred.isCancelled) {
					call.cancel()
				}
			}

			call.enqueue(object : Callback<T> {
				override fun onFailure(call: Call<T>, t: Throwable) {
					deferred.completeExceptionally(t)
				}

				override fun onResponse(call: Call<T>, response: Response<T>) {
					if (response.isSuccessful) {
						deferred.complete(response.body()!!)
					} else {
						deferred.completeExceptionally(HttpException(response))
					}
				}
			})

			return deferred
		}
	}
}