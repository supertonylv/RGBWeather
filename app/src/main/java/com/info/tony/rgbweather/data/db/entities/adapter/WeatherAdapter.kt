package com.info.tony.rgbweather.data.db.entities.adapter

import com.info.tony.rgbweather.data.db.entities.rgblist.*

/**
 * Created by lvlu on 2018/3/11.
 */
abstract class WeatherAdapter{

    abstract fun getCityId(): String

    abstract fun getCityName(): String

    abstract fun getCityNameEn(): String

    abstract fun getWeatherLive(): WeatherLive

    abstract fun getWeatherForecasts(): List<WeatherForecast>

    abstract fun getLifeIndexes(): List<LifeIndex>

    abstract fun getAirQualityLive(): AirQualityLive

    fun getWeather(): Weather {
        return Weather(getCityId(),getCityName(),getCityNameEn(),getWeatherLive(),getWeatherForecasts(),getAirQualityLive(),getLifeIndexes())
    }
}