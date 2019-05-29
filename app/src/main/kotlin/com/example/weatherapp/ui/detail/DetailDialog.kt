package com.example.weatherapp.ui.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.example.weatherapp.R
import com.example.weatherapp.entities.WeatherData
import com.example.weatherapp.utils.loadWeatherIcon
import com.example.weatherapp.utils.toCelsius
import com.example.weatherapp.utils.toDirection
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dg_detail.*


/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

class DetailDialog : BottomSheetDialogFragment() {

	private val weatherData by lazy { arguments?.getSerializable("data") as WeatherData }

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val dialog = super.onCreateDialog(savedInstanceState)

		dialog.setOnShowListener {
			val bottomSheet =
				dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout
			val behavior = BottomSheetBehavior.from(bottomSheet)
			behavior.state = BottomSheetBehavior.STATE_EXPANDED
		}

		return dialog
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? = inflater.inflate(R.layout.dg_detail, container, false)

	@SuppressLint("SetTextI18n")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		detail_city_tv.text = weatherData.cityName
		detail_temp_tv.text = weatherData.temperature.toCelsius(requireContext())
		detail_weather_iv.loadWeatherIcon(weatherData.weather.first().icon)
		detail_humidity_tv.text = "${weatherData.temperature.humidity}%"
		val direction = weatherData.wind.toDirection(requireContext())
		detail_wind_tv.text = "${weatherData.wind.speed} m/s, ${getString(direction.title)}"
		detail_wind_iv.setImageResource(direction.icon)
		detail_pressure_tv.text = "${weatherData.temperature.pressure} mb"
	}

	companion object {
		fun display(manager: FragmentManager, data: WeatherData) {
			val example = DetailDialog()
			example.arguments = Bundle().apply { putSerializable("data", data) }
			example.show(manager, DetailDialog::class.java.simpleName)
		}
	}
}