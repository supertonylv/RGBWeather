package com.info.tony.rgbweather.feature.selectcity

import android.content.Context
import com.info.tony.rgbweather.data.db.dao.CityDao
import com.info.tony.rgbweather.data.db.entities.City
import com.info.tony.rgbweather.di.component.DaggerPresenterComponent
import com.info.tony.rgbweather.di.module.ApplicationModule
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by lvlu on 2018/3/14.
 */
class SelectCityPresenter :SelectCityContract.Presenter{

    private val cityListView: SelectCityContract.View

    private val subscriptions: CompositeDisposable

    @Inject
    lateinit var cityDao: CityDao

    @Inject
    constructor(context: Context, view: SelectCityContract.View) {

        this.cityListView = view
        this.subscriptions = CompositeDisposable()
        cityListView.setPresenter(this)
        DaggerPresenterComponent.builder()
                .applicationModule(ApplicationModule(context))
                .build().inject(this)

    }

    override fun loadCities() {
        val subscription = Observable.just(cityDao.queryCityList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({t: List<City>? ->  cityListView.displayCities(t?: emptyList())})
        subscriptions.add(subscription)
    }

    override fun subscribe() {
        loadCities()
    }

    override fun unSubscribe() {
        subscriptions.clear()
    }

}