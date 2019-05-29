package com.example.weatherapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.utils.inflate
import com.example.weatherapp.utils.replaceFragment
import com.example.weatherapp.ui.cities.CitiesFragment

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

class SplashFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return container?.inflate(R.layout.fr_splash)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		Handler().postDelayed({
			activity?.replaceFragment(CitiesFragment(), R.id.container)
		}, 2500)
	}

}