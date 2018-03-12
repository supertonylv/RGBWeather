package com.info.tony.rgbweather

import android.app.Application
import com.facebook.stetho.Stetho
import com.info.tony.rgbweather.data.http.ApiClient
import com.info.tony.rgbweather.data.http.ApiConstants
import com.info.tony.rgbweather.data.http.configuration.ApiConfiguration1
import com.info.tony.rgbweather.util.DelegatesExt

/**
 * Created by lvlu on 2018/3/11.
 */
class WeatherApplication:Application() {

//    private var instance:WeatherApplication? = null


    companion object {
        var instance : WeatherApplication by DelegatesExt.notNullSingleValue<WeatherApplication>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this;
        ApiClient.init(ApiConfiguration1.build {
            dataSourceType = ApiConstants.WEATHER_DATA_SOURCE_TYPE_ENVIRONMENT_CLOUD
        })
        Stetho.initializeWithDefaults(this)
    }
}