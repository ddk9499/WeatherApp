package com.example.weatherapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.io.Serializable

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

const val API_ADDRESS = "http://api.openweathermap.org/data/2.5/"

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
	val fragmentTransaction = beginTransaction()
	fragmentTransaction.func()
	fragmentTransaction.commit()
}

fun FragmentActivity.replaceFragment(
	fragment: Fragment,
	@IdRes frameId: Int,
	hasBackStackTag: Boolean = false,
	tag: String? = null
) {
	supportFragmentManager.inTransaction {
		replace(frameId, fragment, tag)
		if (hasBackStackTag) addToBackStack(fragment.javaClass.name)
	}
}

fun FragmentActivity.addFragment(
	fragment: Fragment,
	@IdRes frameId: Int,
	hasBackStackTag: Boolean = false,
	tag: String? = null
) {
	supportFragmentManager.inTransaction {
		add(frameId, fragment, tag)
		if (hasBackStackTag) addToBackStack(fragment.javaClass.name)
	}
}

inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, Any>) {
	val intent = Intent(this, T::class.java)
	if (params.isNotEmpty()) fillIntentArguments(intent, params)
	startActivity(intent)
}

fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
	params.forEach {
		when (val value = it.second) {
			null -> intent.putExtra(it.first, null as Serializable?)
			is Int -> intent.putExtra(it.first, value)
			is Long -> intent.putExtra(it.first, value)
			is CharSequence -> intent.putExtra(it.first, value)
			is String -> intent.putExtra(it.first, value)
			is Float -> intent.putExtra(it.first, value)
			is Double -> intent.putExtra(it.first, value)
			is Char -> intent.putExtra(it.first, value)
			is Short -> intent.putExtra(it.first, value)
			is Boolean -> intent.putExtra(it.first, value)
			is Serializable -> intent.putExtra(it.first, value)
			is Bundle -> intent.putExtra(it.first, value)
			is Parcelable -> intent.putExtra(it.first, value)
			is Array<*> -> when {
				value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
				value.isArrayOf<String>() -> intent.putExtra(it.first, value)
				value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
				else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
			}
			is IntArray -> intent.putExtra(it.first, value)
			is LongArray -> intent.putExtra(it.first, value)
			is FloatArray -> intent.putExtra(it.first, value)
			is DoubleArray -> intent.putExtra(it.first, value)
			is CharArray -> intent.putExtra(it.first, value)
			is ShortArray -> intent.putExtra(it.first, value)
			is BooleanArray -> intent.putExtra(it.first, value)
			else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
		}
		return@forEach
	}
}
