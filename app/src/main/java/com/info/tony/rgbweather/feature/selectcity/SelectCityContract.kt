package com.info.tony.rgbweather.feature.selectcity

import com.info.tony.rgbweather.base.BasePresenter
import com.info.tony.rgbweather.base.BaseView
import com.info.tony.rgbweather.data.db.entities.City

/**
 * Created by lvlu on 2018/3/14.
 */
interface SelectCityContract {
    interface View : BaseView<Presenter> {

        fun displayCities(cities: List<City>)
    }

    interface Presenter : BasePresenter {

        fun loadCities()
    }
}