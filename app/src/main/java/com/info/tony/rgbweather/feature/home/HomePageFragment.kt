package com.info.tony.rgbweather.feature.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
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
import com.info.tony.rgbweather.data.WeatherDeatail
import com.info.tony.rgbweather.data.db.entities.rgblist.LifeIndex
import com.info.tony.rgbweather.data.db.entities.rgblist.Weather
import com.info.tony.rgbweather.data.db.entities.rgblist.WeatherForecast
import com.info.tony.widget.IndicatorValueChangeListener
import com.info.tony.widget.IndicatorView
import kotterknife.bindView
import java.util.ArrayList

/**
 * Created by lvlu on 2018/3/15.
 */


class HomePageFragment : BaseFragment(), HomePageContract.View {

    //AQI
    val aqiTextView: TextView? by bindView(R.id.tv_aqi)
    val qualityTextView: TextView? by bindView(R.id.tv_quality)
    internal val aqiIndicatorView: IndicatorView? by bindView(R.id.indicator_view_aqi)
    internal val adviceTextView: TextView? by bindView(R.id.tv_advice)
    internal val cityRankTextView: TextView? by bindView(R.id.tv_city_rank)

    //详细天气信息
    internal val detailRecyclerView: RecyclerView? by bindView(R.id.detail_recycler_view)

    //预报
    internal val forecastRecyclerView: RecyclerView? by bindView(R.id.forecast_recycler_view)

    //生活指数
    internal val lifeIndexRecyclerView: RecyclerView? by bindView(R.id.life_index_recycler_view)

    private var onFragmentInteractionListener: OnFragmentInteractionListener? = null

    private var unbinder: Unbinder? = null

    private var weather: Weather? = null

    private var weatherDetails: MutableList<WeatherDeatail>? = null
    private var weatherForecasts: MutableList<WeatherForecast>? = null
    private var lifeIndices: MutableList<LifeIndex>? = null

    private var detailAdapter: DetailAdapter? = null
    private var forecastAdapter: ForecastAdapter? = null
    private var lifeIndexAdapter: LifeIndexAdapter? = null

    private var presenter: HomePageContract.Presenter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            onFragmentInteractionListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home_page, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //天气详情
        detailRecyclerView?.isNestedScrollingEnabled = false
        detailRecyclerView?.layoutManager = GridLayoutManager(getActivity(), 3)
        weatherDetails = ArrayList<WeatherDeatail>()
        detailAdapter = DetailAdapter(weatherDetails)

        forecastRecyclerView?.itemAnimator = DefaultItemAnimator()
        detailRecyclerView?.adapter = detailAdapter

        //天气预报
        forecastRecyclerView?.isNestedScrollingEnabled = false
        forecastRecyclerView?.layoutManager = LinearLayoutManager(getActivity())
        weatherForecasts = ArrayList<WeatherForecast>()
        forecastAdapter = ForecastAdapter(weatherForecasts)
        forecastRecyclerView?.itemAnimator = DefaultItemAnimator()
        forecastRecyclerView?.adapter = forecastAdapter

        //生活指数
        lifeIndexRecyclerView?.isNestedScrollingEnabled = false
        lifeIndexRecyclerView?.layoutManager = GridLayoutManager(getActivity(), 4)
        lifeIndices = ArrayList<LifeIndex>()
        lifeIndexAdapter = LifeIndexAdapter(context!!, lifeIndices)
        lifeIndexRecyclerView?.itemAnimator = DefaultItemAnimator()
        lifeIndexRecyclerView?.adapter = lifeIndexAdapter

        aqiIndicatorView?.setIndicatorValueChangeListener(object :IndicatorValueChangeListener{
            override fun onChange(currentIndicatorValue: Int, stateDescription: String, indicatorTextColor: Int) {
                aqiTextView?.text = currentIndicatorValue.toString()
                if (TextUtils.isEmpty(weather?.airQualityLive?.quality)) {
                    qualityTextView?.text = stateDescription
                } else {
                    qualityTextView?.text = weather?.airQualityLive?.quality
                }
                aqiTextView!!.setTextColor(indicatorTextColor)
                qualityTextView!!.setTextColor(indicatorTextColor)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        assert(presenter != null)
        presenter!!.subscribe()
    }

    @SuppressLint("SetTextI18n")
    override fun displayWeatherInformation(weather: Weather) {

        this.weather = weather
        onFragmentInteractionListener!!.updatePageTitle(weather)

        val airQualityLive = weather.airQualityLive
        aqiIndicatorView?.setIndicatorValue(airQualityLive?.aqi ?: 0)
        adviceTextView?.text = airQualityLive?.advice
        val rank = airQualityLive?.cityRank
        cityRankTextView?.text = if (TextUtils.isEmpty(rank)) "首要污染物: " + airQualityLive?.primary else rank

        weatherDetails?.clear()
        weatherDetails?.addAll(createDetails(weather))
        detailAdapter?.notifyDataSetChanged()

        weatherForecasts?.clear()
        weather.weatherForecasts?.let { weatherForecasts?.addAll(it) }
        forecastAdapter?.notifyDataSetChanged()

        lifeIndices?.clear()
        weather.lifeIndexes?.let { lifeIndices?.addAll(it) }
        lifeIndexAdapter?.notifyDataSetChanged()

        onFragmentInteractionListener?.addOrUpdateCityListInDrawerMenu(weather)
    }

    private fun createDetails(weather: Weather): List<WeatherDeatail> {

        val details = ArrayList<WeatherDeatail>()
        details.add(WeatherDeatail(R.drawable.ic_temperature, "体感温度", weather.weatherLive?.feelsTemperature + "°C"))
        details.add(WeatherDeatail(R.drawable.ic_humidity, "湿度", weather.weatherLive?.humidity + "%"))
        //        details.add(new WeatherDetail(R.drawable.ic_index_sunscreen, "气压", (int) Double.parseDouble(weather.getWeatherLive().getAirPressure()) + "hPa"));
        details.add(WeatherDeatail(R.drawable.ic_sunny_ray, "紫外线指数", weather.weatherForecasts?.get(0)?.uv))
        details.add(WeatherDeatail(R.drawable.ic_precipitation, "降水量", weather.weatherLive?.rain + "mm"))
        details.add(WeatherDeatail(R.drawable.ic_percent, "降水概率", weather.weatherForecasts?.get(0)?.pop + "%"))
        details.add(WeatherDeatail(R.drawable.ic_if_visibility_off, "能见度", weather.weatherForecasts?.get(0)?.visibility + "km"))
        return details
    }

    override fun setPresenter(presenter: HomePageContract.Presenter) {
        this.presenter = presenter
    }

    override fun onPause() {
        super.onPause()
        presenter?.unSubscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    interface OnFragmentInteractionListener {
        fun updatePageTitle(weather: Weather)

        /**
         * 更新完天气数据同时需要刷新侧边栏的已添加的城市列表
         *
         * @param weather 天气数据
         */
        fun addOrUpdateCityListInDrawerMenu(weather: Weather)
    }

    companion object {

        fun newInstance(): HomePageFragment {

            return HomePageFragment()
        }
    }
}