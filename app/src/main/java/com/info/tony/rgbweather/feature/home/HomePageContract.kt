package com.info.tony.rgbweather.feature.home

import com.info.tony.rgbweather.base.BasePresenter
import com.info.tony.rgbweather.base.BaseView
import com.info.tony.rgbweather.data.db.entities.rgblist.Weather

/**
 * Created by lvlu on 2018/3/15.
 */
interface HomePageContract {
    interface View : BaseView<Presenter> {

        fun displayWeatherInformation(weather: Weather)
    }

    interface Presenter : BasePresenter {

        fun loadWeather(cityId: String, refreshNow: Boolean)
    }
}