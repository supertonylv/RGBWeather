package com.info.tony.rgbweather.data.db.entities.adapter

import com.info.tony.library.util.DateConvertUtils
import com.info.tony.rgbweather.data.db.entities.rgblist.AirQualityLive
import com.info.tony.rgbweather.data.db.entities.rgblist.LifeIndex
import com.info.tony.rgbweather.data.db.entities.rgblist.WeatherForecast
import com.info.tony.rgbweather.data.db.entities.rgblist.WeatherLive
import com.info.tony.rgbweather.data.http.entity.know.KnowWeather

/**
 * Created by lvlu on 2018/3/11.
 */
open class KnowWeatherAdapter:WeatherAdapter {

    private var knowWeather: KnowWeather

    constructor(knowWeather: KnowWeather){
        this.knowWeather = knowWeather
    }

    override fun getCityId(): String {
        return knowWeather.cityId ?:""
    }

    override fun getCityName(): String {
        return knowWeather.basic?.city ?: ""
    }

    override fun getCityNameEn(): String {
        return ""
    }

    override fun getWeatherLive(): WeatherLive {

        return WeatherLive(
                cityId = knowWeather.cityId,
                humidity = "",
                temp = knowWeather.basic?.temp,
                time = DateConvertUtils.dateToTimeStamp(knowWeather.basic?.time?:"", DateConvertUtils.DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM_SS),
                weather = knowWeather.basic?.weather,
                wind = "",
                windSpeed = ""


        )
    }

    override fun getWeatherForecasts(): List<WeatherForecast> {

        val weatherForecasts = ArrayList<WeatherForecast>()
        knowWeather.dailyForecast?.forEach {
            val temperature = splitTemperature(it.temp_range)
            var weatherForecast = WeatherForecast(
                    cityId = knowWeather.cityId,
                    wind = "",
                    weather = it.weather,
                    week = it.week,
                    date = it.date,
                    tempMax = if(temperature!= null) temperature[1] else 0,
                    tempMin = if(temperature != null) temperature[0] else 0
            )
            weatherForecasts.add(weatherForecast)
        }
        return weatherForecasts
    }

    override fun getLifeIndexes(): List<LifeIndex> {

        val indexList = ArrayList<LifeIndex>()
        knowWeather.lifeIndex?.forEach {
            indexList.add(LifeIndex(
                    cityId = knowWeather.cityId,
                    name = it.name,
                    index = it.level,
                    details = it.content
            ))
        }
        return indexList
    }

    override fun getAirQualityLive(): AirQualityLive {
        return AirQualityLive(
                cityId = knowWeather.cityId,
                aqi = knowWeather.aqi?.aqi?.toInt() ?: -1,
                pm25 = knowWeather.aqi?.pm25?.toInt() ?: -1,
                pm10 = knowWeather.aqi?.pm10?.toInt() ?: 0,
                advice = knowWeather.aqi?.advice,
                cityRank = knowWeather.aqi?.cityRank,
                quality = knowWeather.aqi?.quality
        )
    }

    /**
     * 拆分气温
     *
     * @param temperatureRange 如：-6~2°
     * @return {-6, 2}
     */
    private fun splitTemperature(temperatureRange: String?): IntArray? {
        var temperatureRange = temperatureRange
        if (temperatureRange != null && temperatureRange.contains("~")) {
            if (temperatureRange.contains("°")) {
                temperatureRange = temperatureRange.replace("°".toRegex(), "")
            }
            val temps = temperatureRange.split("~".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return intArrayOf(Integer.parseInt(temps[0]), Integer.parseInt(temps[1]))
        } else {
            return null
        }
    }
}