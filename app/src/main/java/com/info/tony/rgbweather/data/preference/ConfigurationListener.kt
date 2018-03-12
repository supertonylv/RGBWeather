package com.info.tony.rgbweather.data.preference

/**
 * Created by lvlu on 2018/3/11.
 */
interface ConfigurationListener {
    fun onConfigurationChanged(pref:WeatherSettings, newValue:Any?)
}