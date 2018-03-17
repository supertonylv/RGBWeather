package com.info.tony.rgbweather.feature.home.drawer

import com.info.tony.rgbweather.base.BasePresenter
import com.info.tony.rgbweather.base.BaseView
import com.info.tony.rgbweather.data.db.entities.rgblist.Weather
import java.io.InvalidClassException

/**
 * Created by lvlu on 2018/3/9.
 */
interface DrawerContract {

    interface View : BaseView<DrawerMenuPresenter> {

        fun displaySavedCities(weatherList: List<Weather>?)
    }

    interface Presenter : BasePresenter {

        fun loadSavedCities()

        fun deleteCity(cityId: String)

        @Throws(InvalidClassException::class)
        fun saveCurrentCityToPreference(cityId: String)
    }
}
