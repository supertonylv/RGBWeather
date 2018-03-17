package com.info.tony.rgbweather

import android.app.Application
import com.facebook.stetho.Stetho
import com.info.tony.rgbweather.data.http.ApiClient
import com.info.tony.rgbweather.data.http.ApiConstants
import com.info.tony.rgbweather.data.http.configuration.ApiConfiguration1
import com.info.tony.rgbweather.di.component.ApplicationComponent
import com.info.tony.rgbweather.di.component.DaggerApplicationComponent
import com.info.tony.rgbweather.di.module.ApplicationModule
import com.info.tony.rgbweather.util.DelegatesExt
import com.j256.ormlite.logger.LocalLog
import dagger.android.DaggerApplication

/**
 * Created by lvlu on 2018/3/11.
 */
class WeatherApplication:Application() {

//    private var instance:WeatherApplication? = null


    var applicationComponent:ApplicationComponent? = null

    companion object {
        var instance : WeatherApplication by DelegatesExt.notNullSingleValue<WeatherApplication>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ApiClient.init(ApiConfiguration1.build {
            dataSourceType = ApiConstants.WEATHER_DATA_SOURCE_TYPE_ENVIRONMENT_CLOUD
        })
        Stetho.initializeWithDefaults(this)
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}