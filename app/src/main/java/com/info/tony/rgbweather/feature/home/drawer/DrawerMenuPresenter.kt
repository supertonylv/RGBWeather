package com.info.tony.rgbweather.feature.home.drawer

import android.content.Context
import com.info.tony.rgbweather.data.db.dao.WeatherDao
import com.info.tony.rgbweather.data.preference.PreferenceHelper
import com.info.tony.rgbweather.data.preference.WeatherSettings
import com.info.tony.rgbweather.di.component.DaggerPresenterComponent
import com.info.tony.rgbweather.di.module.ApplicationModule
import com.info.tony.rgbweather.di.scope.ActivityScoped
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.InvalidClassException
import java.sql.SQLException
import javax.inject.Inject

/**
 * Created by lvlu on 2018/3/16.
 */
@ActivityScoped
class DrawerMenuPresenter @Inject constructor(context: Context, val view: DrawerContract.View) : DrawerContract.Presenter {


    private val subscriptions: CompositeDisposable

    @Inject
    lateinit var weatherDao: WeatherDao

    init {
        this.subscriptions = CompositeDisposable()
        view.setPresenter(this)

        DaggerPresenterComponent.builder()
                .applicationModule(ApplicationModule(context))
                .build().inject(this)
    }

    override fun subscribe() {
        loadSavedCities()
    }

    override fun unSubscribe() {
        subscriptions.clear()
    }

    override fun loadSavedCities() {

        try {
            val subscription = Observable.just(weatherDao.queryAllSaveCity())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ weathers -> view.displaySavedCities(weathers) })
            subscriptions.add(subscription)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    override fun deleteCity(cityId: String) {

        Observable.just(deleteCityFromDBAndReturnCurrentCityId(cityId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currentCityId ->
                    try {
                        if (currentCityId != null)
                            PreferenceHelper.savePreference(WeatherSettings.SETTINGS_CURRENT_CITY_ID, currentCityId)
                    } catch (e: InvalidClassException) {
                        e.printStackTrace()
                    }
                })
    }

    @Throws(InvalidClassException::class)
    override fun saveCurrentCityToPreference(cityId: String) {
        PreferenceHelper.savePreference(WeatherSettings.SETTINGS_CURRENT_CITY_ID, cityId)
    }

    private fun deleteCityFromDBAndReturnCurrentCityId(cityId: String): String {
        var currentCityId = PreferenceHelper.getSharedPreferences().getString(WeatherSettings.SETTINGS_CURRENT_CITY_ID.getId(), "")
        try {
            weatherDao?.deleteById(cityId)
            if (cityId == currentCityId) {//说明删除的是当前选择的城市，所以需要重新设置默认城市
                val weatherList = weatherDao!!.queryAllSaveCity()
                if (weatherList != null && weatherList?.size > 0) {
                    currentCityId = weatherList?.get(0).cityId
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return currentCityId
    }


}