package com.example.weatherapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

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
