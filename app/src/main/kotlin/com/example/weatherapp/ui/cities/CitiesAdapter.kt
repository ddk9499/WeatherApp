package com.example.weatherapp.ui.cities

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.entities.WeatherData
import com.example.weatherapp.utils.inflate
import com.example.weatherapp.utils.loadWeatherIcon
import com.example.weatherapp.utils.toCelsius
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_city.view.*

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

class CitiesAdapter(
	private val listener: (WeatherData) -> Unit
) : RecyclerView.Adapter<CitiesAdapter.CitiesHolder>() {

	private val list: MutableList<WeatherData> = mutableListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		CitiesHolder(parent.inflate(R.layout.item_city))

	override fun onBindViewHolder(holder: CitiesHolder, position: Int) = holder.bind(list[position])

	override fun getItemCount() = list.size

	fun addElements(elements: List<WeatherData>) {
		list.clear()
		list.addAll(elements)
		notifyDataSetChanged()
	}

	inner class CitiesHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {

		override val containerView get() = itemView

		fun bind(weatherData: WeatherData) = with(containerView) {
			city_name_tv.text = weatherData.cityName
			temperature_tv.text = weatherData.temperature.toCelsius(context)
			weather_icon.loadWeatherIcon(weatherData.weather.first().icon)

			setOnClickListener { listener.invoke(weatherData) }
		}
	}
}