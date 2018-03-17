package com.info.tony.rgbweather.feature

import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.data.db.entities.rgblist.WeatherForecast

/**
 * Created by lvlu on 2018/3/17.
 */
object WeatherDrawableUtil {

    fun getDrawableByWeather(weather: String?):Int{
        var drawale:Int
        if(weather?.contains("特大暴雪")?:false) {
            drawale = R.drawable.ic_heavy_snow_heavy
        } else if(weather?.contains("特大暴雨")?:false) {
            drawale = R.drawable.ic_heavy_rain_to_heavy
        } else if(weather?.contains("大暴雪")?:false) {
            drawale = R.drawable.ic_heavy_snow
        } else if(weather?.contains("大暴雨")?:false) {
            drawale = R.drawable.ic_heavy_rain_1
        } else if(weather?.contains("中雪")?:false) {
            drawale = R.drawable.ic_moderate_snow
        }else if(weather?.contains("中雨")?:false) {
            drawale = R.drawable.ic_moderate_rain
        }else if(weather?.contains("小雪")?:false) {
            drawale = R.drawable.ic_small_snow
        }else if(weather?.contains("小雨")?:false) {
            drawale = R.drawable.ic_small_rain
        }else if(weather?.contains("阵雪")?:false) {
            drawale = R.drawable.ic_snow_shower
        }else if(weather?.contains("阵雨")?:false) {
            drawale = R.drawable.ic_rain_shower
        }else if(weather?.contains("冻雨")?:false) {
            drawale = R.drawable.ic_ice_rain
        }else if(weather?.contains("雷阵雨")?:false) {
            drawale = R.drawable.ic_thundershower
        }else if(weather?.contains("沙尘暴")?:false) {
            drawale = R.drawable.ic_san_storm
        }else if(weather?.contains("雾")?:false) {
            drawale = R.drawable.ic_fog
        }else if(weather?.contains("阴")?:false) {
            drawale = R.drawable.ic_cloudy
        }else if(weather?.contains("多云")?:false) {
            drawale = R.drawable.ic_overcast
        }else if(weather?.contains("晴")?:false) {
            drawale = R.drawable.ic_sunny
        }else if(weather?.contains("雨夹雪")?:false) {
            drawale = R.drawable.ic_sleet
        } else {
            drawale = R.drawable.ic_na
        }
        return drawale
    }
}