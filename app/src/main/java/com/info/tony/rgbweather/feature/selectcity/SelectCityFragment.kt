package com.info.tony.rgbweather.feature.selectcity


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.base.BaseFragment
import com.info.tony.rgbweather.data.db.entities.City
import com.info.tony.rgbweather.data.preference.PreferenceHelper
import com.info.tony.rgbweather.data.preference.WeatherSettings
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.jakewharton.rxbinding2.view.RxViewGroup
import kotterknife.bindView
import org.jetbrains.anko.support.v4.toast
import java.io.InvalidClassException
import java.util.ArrayList
import kotlinx.android.synthetic.main.fragment_select_city.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find


/**
 * A simple [Fragment] subclass.
 * Use the [SelectCityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class SelectCityFragment : BaseFragment,SelectCityContract.View {

    var cities: MutableList<City> = emptyList<City>().toMutableList()
    var cityListAdapter: CityListAdapter? = null

    var recyclerView: RecyclerView? = null
//    val recyclerView: RecyclerView by bindView<RecyclerView>(R.id.rv_city_list)
//    var unbinder: Unbinder? = null

    var selectCityPresenter: SelectCityContract.Presenter? = null

    constructor()

    companion object {

    fun newInstance(): SelectCityFragment {
        return SelectCityFragment()
    }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_select_city, container, false)
//        unbinder = ButterKnife.bind(this, rootView)
        recyclerView = rootView.find(R.id.SC_recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        cityListAdapter = CityListAdapter(cities,
                object : CityListAdapter.OnItemClickListener{
                    override fun invoke(city: City) {
                        PreferenceHelper.savePreference(WeatherSettings.SETTINGS_CURRENT_CITY_ID,city?.cityId?:"")
                        toast(city.cityName.toString())
                        try {
                            activity?.let { activity?.finish() }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                })

        recyclerView?.adapter = cityListAdapter
        selectCityPresenter?.subscribe()
        return rootView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        selectCityPresenter?.unSubscribe()
    }


    override fun displayCities(cities: List<City>) {
        cityListAdapter?.dataChange(cities)
    }

    override fun setPresenter(presenter: SelectCityContract.Presenter) {
        this.selectCityPresenter = presenter
    }

}
