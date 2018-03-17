package com.info.tony.rgbweather.feature.home.drawer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.base.BaseFragment
import com.info.tony.rgbweather.data.db.entities.rgblist.Weather
import com.info.tony.rgbweather.feature.selectcity.SelectCityActivity
import kotterknife.bindView
import java.io.InvalidClassException
import java.util.ArrayList

/**
 * Created by lvlu on 2018/3/16.
 */


class DrawerMenuFragment : BaseFragment(), DrawerContract.View {


    val addCityButton: ImageView? by bindView(R.id.add_city_btn)
    val cityManagerRecyclerView: RecyclerView? by bindView(R.id.rv_city_manager)

    private var columnCount = 3
    private var weatherList: MutableList<Weather>? = emptyList<Weather>().toMutableList()
    private val cityManagerAdapter: CityManagerAdapter by lazy { CityManagerAdapter(weatherList) }

    private var presenter: DrawerContract.Presenter? = null

    private var onSelectCity: OnSelectCity? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSelectCity) {
            onSelectCity = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getArguments() != null) {
            columnCount = getArguments()?.getInt(ARG_COLUMN_COUNT) ?: 1
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_drawer_menu, container, false)

//        cityManagerAdapter!!.setOnItemClickListener(object : CityManagerAdapter.OnCityManagerItemClickListener() {
//
//            fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                try {
//                    presenter!!.saveCurrentCityToPreference(weatherList!![position].getCityId())
//                    onSelectCity!!.onSelect(weatherList!![position].getCityId())
//                } catch (e: InvalidClassException) {
//                    e.printStackTrace()
//                }
//
//            }
//
//            fun onDeleteClick(cityId: String) {
//                presenter!!.deleteCity(cityId)
//            }
//        })


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (columnCount <= 1) {
            cityManagerRecyclerView?.layoutManager = LinearLayoutManager(context)
        } else {
            cityManagerRecyclerView?.layoutManager = GridLayoutManager(context, columnCount)
        }
        cityManagerRecyclerView?.itemAnimator = DefaultItemAnimator()
        weatherList = emptyList<Weather>().toMutableList()
        cityManagerRecyclerView?.adapter = cityManagerAdapter
        addCityButton?.setOnClickListener{
            val intent = Intent(getActivity(), SelectCityActivity::class.java)
            startActivity(intent)
        }
        cityManagerAdapter.setOnCityClickListener { position, weather -> run{
            try {
                    presenter?.saveCurrentCityToPreference(weather.cityId?:"101020100")
                    onSelectCity?.onSelect(weather.cityId?:"101020100")
                } catch (e: InvalidClassException) {
                    e.printStackTrace()
                }
        } }
        cityManagerAdapter.setOnDeleteClickListener { position, weather -> kotlin.run {
            var cityId = weather.cityId;
            cityId?.let {presenter?.deleteCity(cityId)  }
            weatherList?.let { weatherList?.remove(weather) }
            cityManagerAdapter.notifyItemRemoved(position)

        }}
        presenter?.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.unSubscribe()
    }

    override fun displaySavedCities(list: List<Weather>?) {
        this.weatherList?.clear()
        list?.let { weatherList?.addAll(list) }
        cityManagerAdapter?.notifyDataSetChanged()
    }

    override fun setPresenter(presenter: DrawerMenuPresenter) {

        this.presenter = presenter
    }


    interface OnSelectCity {

        fun onSelect(cityId: String)
    }

    companion object {


        private val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int): DrawerMenuFragment {
            val fragment = DrawerMenuFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.setArguments(args)
            return fragment
        }
    }
}