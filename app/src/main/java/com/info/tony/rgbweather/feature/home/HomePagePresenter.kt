package com.info.tony.rgbweather.feature.home

import android.content.Context
import android.widget.Toast
import com.info.tony.library.util.RxSchedulerUtils
import com.info.tony.rgbweather.data.db.dao.WeatherDao
import com.info.tony.rgbweather.data.db.entities.rgblist.Weather
import com.info.tony.rgbweather.data.preference.PreferenceHelper
import com.info.tony.rgbweather.data.preference.WeatherSettings
import com.info.tony.rgbweather.data.repository.WeatherDataRepository
import com.info.tony.rgbweather.di.component.DaggerPresenterComponent
import com.info.tony.rgbweather.di.module.ApplicationModule
import com.info.tony.rgbweather.di.scope.ActivityScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by lvlu on 2018/3/15.
 */
@ActivityScoped
class HomePagePresenter : HomePageContract.Presenter {

    val subscriptions: CompositeDisposable

    var context:Context
    var weatherView:HomePageContract.View

    @Inject constructor( context: Context,  view: HomePageContract.View){
        this.context = context;
        this.weatherView = view;
        weatherView.setPresenter(this)
        DaggerPresenterComponent.builder()
                .applicationModule(ApplicationModule(context))
                .build().inject(this)
    }

    @Inject
    lateinit var weatherDao: WeatherDao

    init {
        this.subscriptions = CompositeDisposable()
//        weatherView.setPresenter(this)
    }

    override fun subscribe() {
        val cityId = PreferenceHelper.getSharedPreferences().getString(WeatherSettings.SETTINGS_CURRENT_CITY_ID.getId(), "")
        loadWeather(cityId, false)
    }

    override fun loadWeather(cityId: String, refreshNow: Boolean) {

        val subscription = WeatherDataRepository.getWeather(context, cityId, weatherDao, refreshNow)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { weather:Weather -> run{weatherView?.displayWeatherInformation(weather)} }
        subscription?.let { subscriptions.add(it) }
    }

    override fun unSubscribe() {
        subscriptions.clear()
    }
}