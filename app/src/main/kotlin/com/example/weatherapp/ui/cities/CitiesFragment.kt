package com.example.weatherapp.ui.cities

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.weatherapp.R
import com.example.weatherapp.entities.WeatherData
import com.example.weatherapp.model.remote.NetworkUtils
import com.example.weatherapp.ui.detail.DetailDialog
import com.example.weatherapp.utils.inflate
import com.example.weatherapp.utils.visible
import kotlinx.android.synthetic.main.fr_cities.*
import kotlinx.android.synthetic.main.layout_error.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

/**
 *
 * Created at May 2019
 *
 * @project WeatherApp
 * @author Dostonbek Kamalov (aka @ddk9499)
 */

class CitiesFragment : MvpAppCompatFragment(), CitiesView {

	@InjectPresenter
	lateinit var presenter: CitiesPresenter

	@ProvidePresenter
	fun providePresenter(): CitiesPresenter = CitiesPresenter(get(), get())

	private val netUtils by inject<NetworkUtils>()
	private val adapter by lazy { CitiesAdapter { openDetails(it) } }
	private val isPortrait by lazy { resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT }
	private val addCityDialog by lazy {
		AlertDialog.Builder(requireContext()).create().also {
			val dialogView = layoutInflater.inflate(R.layout.dg_add_city, null)
			val cityCodeEditText = dialogView.findViewById<EditText>(R.id.city_code_et)
			val addBtn = dialogView.findViewById<Button>(R.id.add_city_btn)
			addBtn.setOnClickListener {
				cityCodeEditText.error = null
				presenter.addCity(cityCodeEditText.text.toString())
			}
			it.setTitle(R.string.add_city)
			it.setView(dialogView)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? = container?.inflate(R.layout.fr_cities)

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		val manager =
			if (isPortrait) LinearLayoutManager(context) else GridLayoutManager(context, 2)

		cities_rv.apply {
			layoutManager = manager
			adapter = this@CitiesFragment.adapter
			setHasFixedSize(true)
		}

		cities_sr.setOnRefreshListener { refresh() }

		retry_btn.setOnClickListener { refresh() }
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		menu.add(R.string.add).apply {
			setOnMenuItemClickListener { _ ->
				addCityDialog.show()
				true
			}
			setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
		}
	}

	override fun wrongCode() {
		addCityDialog.findViewById<EditText>(R.id.city_code_et)?.error = "Wrong"
	}

	override fun hideDialog() {
		addCityDialog.dismiss()
	}

	override fun showProgress(isVisible: Boolean) {
		cities_rv.visible = !isVisible
		cities_sr.isRefreshing = isVisible
	}

	override fun noInternetConnection() {
		cities_sr.isEnabled = false
		error_layout.visible = true
	}

	override fun showWeathersTable(items: List<WeatherData>) {
		adapter.addElements(items)
		cities_sr.isEnabled = true
	}

	private fun refresh() {
		if (netUtils.isOnline()) {
			error_layout.visible = false
			presenter.loadWeatherData()
		} else {
			noInternetConnection()
		}
	}

	private fun openDetails(data: WeatherData) {
		DetailDialog.display(requireFragmentManager(), data)
	}
}
