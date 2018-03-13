package com.info.tony.rgbweather.data.db.entities.adapter

import com.info.tony.library.util.DateConvertUtils
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.WeatherApplication
import com.info.tony.rgbweather.data.db.entities.rgblist.AirQualityLive
import com.info.tony.rgbweather.data.db.entities.rgblist.LifeIndex
import com.info.tony.rgbweather.data.db.entities.rgblist.WeatherForecast
import com.info.tony.rgbweather.data.db.entities.rgblist.WeatherLive
import com.info.tony.rgbweather.data.http.entity.envicloud.EnvironmentCloudCityAirLive
import com.info.tony.rgbweather.data.http.entity.envicloud.EnvironmentCloudForecast
import com.info.tony.rgbweather.data.http.entity.envicloud.EnvironmentCloudWeatherLive

/**
 * Created by lvlu on 2018/3/11.
 */
open class CloudWeatherAdapter:WeatherAdapter {
    override fun getAirQualityLive(): AirQualityLive {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var cloudWeatherLive: EnvironmentCloudWeatherLive? = null
    private var cloudForecast: EnvironmentCloudForecast? = null
    private var cloudCityAirLive: EnvironmentCloudCityAirLive? = null

    constructor(cloudWeatherLive: EnvironmentCloudWeatherLive?, cloudForecast: EnvironmentCloudForecast?, cloudCityAirLive: EnvironmentCloudCityAirLive?){
        this.cloudWeatherLive = cloudWeatherLive
        this.cloudForecast = cloudForecast
        this.cloudCityAirLive = cloudCityAirLive
    }

    override fun getCityId(): String {
        return cloudWeatherLive?.cityId ?:""
    }

    override fun getCityName(): String {
        return cloudForecast?.cityName ?: ""
    }

    override fun getCityNameEn(): String {
        return ""
    }

    override fun getWeatherLive(): WeatherLive {
        var date:Long = DateConvertUtils.dateToTimeStamp(cloudWeatherLive?.updateTime ?: "",DateConvertUtils.DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM)
        val weatherLive = WeatherLive(cloudWeatherLive?.cityId,cloudWeatherLive?.phenomena,cloudWeatherLive?.temperature,cloudWeatherLive?.humidity,
                cloudWeatherLive?.windDirect,cloudWeatherLive?.windSpeed,date,cloudWeatherLive?.windPower,cloudWeatherLive?.rain,
                cloudWeatherLive?.feelsTemperature,cloudWeatherLive?.airPressure)
        return weatherLive
    }

    override fun getWeatherForecasts(): List<WeatherForecast> {

//        val weatherForecasts = ArrayList<WeatherForecast>()
        var weatherForecasts = emptyList<WeatherForecast>()

        cloudForecast?.forecast?.forEach {
            val weatherForecast = WeatherForecast(
                    cityId = getCityId(),
//                    weather = it.pres
                    weatherDay = it.cond?.cond_d,
                    weatherNight = it.cond?.cond_n,
                    tempMax = it.tmp?.max?.toInt() ?:0,
                    tempMin = it.tmp?.min?.toInt() ?: 0,
                    wind = it.wind?.dir,
                    date = DateConvertUtils.convertDataToString(it?.date ?:""),
                    week = DateConvertUtils.convertDataToWeek(it?.date ?: ""),
                    pop = it.pop,
                    uv = it.uv,
                    visibility = it.vis,
                    humidity = it.hum,
                    pressure = it.pres,
                    precipitation = it.pres,
                    sunrise = it.astro?.sr,
                    sunset = it.astro?.ss,
                    moonrise = it.astro?.mr,
                    moonset = it.astro?.ms
            )
            weatherForecasts += weatherForecast
        }
        return weatherForecasts
    }

    override fun getLifeIndexes(): List<LifeIndex> {

        val suggestionEntity = cloudForecast?.suggestion

        val indexList = ArrayList<LifeIndex>()

        val index1 = LifeIndex(
                cityId = cloudForecast?.cityId,
                name = WeatherApplication.instance.getString(R.string.air_quality),
                index = cloudForecast?.suggestion?.air?.brf,
                details = cloudForecast?.suggestion?.air?.txt
        )
        indexList.add(index1)

        indexList.add(index1.copy(name = WeatherApplication.instance.getString(R.string.comfor_level)))
        indexList.add(index1.copy(name = WeatherApplication.instance.getString(R.string.dressing_index)))
        indexList.add(index1.copy(name = WeatherApplication.instance.getString(R.string.cold)))
        indexList.add(index1.copy(name = WeatherApplication.instance.getString(R.string.sports)))
        indexList.add(index1.copy(name = WeatherApplication.instance.getString(R.string.travel)))
        indexList.add(index1.copy(name = WeatherApplication.instance.getString(R.string.ultraviolet)))
        indexList.add(index1.copy(name = WeatherApplication.instance.getString(R.string.car_wash)))

        return indexList
    }

    private fun getAqiQuality(aqi: Int): String? {

        if (aqi <= 50) {
            return "优"
        } else if (aqi > 50 && aqi <= 100) {
            return "良"
        } else if (aqi > 100 && aqi <= 150) {
            return "轻度污染"
        } else if (aqi > 150 && aqi <= 200) {
            return "中度污染"
        } else if (aqi > 200 && aqi <= 300) {
            return "重度污染"
        } else if (aqi > 300 && aqi < 500) {
            return "严重污染"
        } else if (aqi >= 500) {
            return "污染爆表"
        }
        return null
    }
}