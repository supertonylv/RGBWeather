package com.info.tony.rgbweather.data.db.entities.adapter

import com.info.tony.library.util.DateConvertUtils
import com.info.tony.rgbweather.data.db.entities.rgblist.AirQualityLive
import com.info.tony.rgbweather.data.db.entities.rgblist.LifeIndex
import com.info.tony.rgbweather.data.db.entities.rgblist.WeatherForecast
import com.info.tony.rgbweather.data.db.entities.rgblist.WeatherLive
import com.info.tony.rgbweather.data.http.entity.mi.MiWeather

/**
 * Created by lvlu on 2018/3/11.
 */
class MiWeatherAdapter:WeatherAdapter {
    private var miWeather: MiWeather

    constructor(miWeather : MiWeather){
        this.miWeather = miWeather
    }

    override fun getCityId(): String {
        return miWeather.aqi?.cityId.toString()
    }

    override fun getCityName(): String {
        return miWeather.aqi?.cityName.toString()
    }

    override fun getCityNameEn(): String {
        return miWeather.forecast?.cityEn.toString()
    }

    override fun getWeatherLive(): WeatherLive {
        return WeatherLive(miWeather.realTime?.cityId,
                miWeather.realTime?.weather, miWeather.realTime?.temp,
                miWeather.realTime?.humidity, miWeather.realTime?.wind,
                miWeather.realTime?.windSpeed,
                DateConvertUtils.dateToTimeStamp(miWeather.realTime?.time ?: "", DateConvertUtils.DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM))
    }

    override fun getWeatherForecasts(): List<WeatherForecast> {

        val weatherForecasts = ArrayList<WeatherForecast>()

        val miForecast = miWeather.forecast

        //TODO Forecast中的日期和星期还需要修改
        val weathers1 = splitWeather(miForecast?.weather1)
        val temps1 = splitTemperature(miForecast?.temp1)
        weatherForecasts.add(WeatherForecast(cityId = miForecast?.cityId, weather = miForecast?.weather1, weatherDay = weathers1[0],
                weatherNight = weathers1[1], tempMax = if(temps1 != null ) temps1[0] else -1,
                tempMin = if (temps1 != null) temps1[1] else -1, wind = miForecast?.wind1, date = miForecast?.date,
                week = DateConvertUtils.convertDataToWeek(miForecast?.date ?: "")))

        val weathers2 = splitWeather(miForecast?.weather2)
        val temps2 = splitTemperature(miForecast?.temp2)
        weatherForecasts.add(WeatherForecast(cityId = miForecast?.cityId, weather = miForecast?.weather1, weatherDay = weathers2[0],
                weatherNight = weathers2[1], tempMax = if(temps2 != null ) temps2[0] else -1,
                tempMin = if (temps2 != null) temps2[1] else -1, wind = miForecast?.wind2, date = miForecast?.date,
                week = DateConvertUtils.convertDataToWeek(miForecast?.date ?: "")))
        val weathers3 = splitWeather(miForecast?.weather3)
        val temps3 = splitTemperature(miForecast?.temp3)
        weatherForecasts.add(WeatherForecast(cityId = miForecast?.cityId, weather = miForecast?.weather3, weatherDay = weathers3[0],
                weatherNight = weathers3[1], tempMax = if(temps3 != null ) temps3[0] else -1,
                tempMin = if (temps3 != null) temps3[1] else -1, wind = miForecast?.wind3, date = miForecast?.date,
                week = DateConvertUtils.convertDataToWeek(miForecast?.date ?: "")))
        val weathers4 = splitWeather(miForecast?.weather4)
        val temps4 = splitTemperature(miForecast?.temp4)
        weatherForecasts.add(WeatherForecast(cityId = miForecast?.cityId, weather = miForecast?.weather4, weatherDay = weathers4[0],
                weatherNight = weathers4[1], tempMax = if(temps4 != null ) temps4[0] else -1,
                tempMin = if (temps4 != null) temps4[1] else -1, wind = miForecast?.wind4, date = miForecast?.date,
                week = DateConvertUtils.convertDataToWeek(miForecast?.date ?: "")))
        val weathers5 = splitWeather(miForecast?.weather5)
        val temps5 = splitTemperature(miForecast?.temp5)
        weatherForecasts.add(WeatherForecast(cityId = miForecast?.cityId, weather = miForecast?.weather5, weatherDay = weathers5[0],
                weatherNight = weathers5[1], tempMax = if(temps5 != null ) temps5[0] else -1,
                tempMin = if (temps5 != null) temps5[1] else -1, wind = miForecast?.wind5, date = miForecast?.date,
                week = DateConvertUtils.convertDataToWeek(miForecast?.date ?: "")))
        val weathers6 = splitWeather(miForecast?.weather6)
        val temps6 = splitTemperature(miForecast?.temp6)
        weatherForecasts.add(WeatherForecast(cityId = miForecast?.cityId, weather = miForecast?.weather6, weatherDay = weathers6[0],
                weatherNight = weathers6[1], tempMax = if(temps6 != null ) temps6[0] else -1,
                tempMin = if (temps6 != null) temps6[1] else -1, wind = miForecast?.wind6, date = miForecast?.date,
                week = DateConvertUtils.convertDataToWeek(miForecast?.date ?: "")))
        return weatherForecasts
    }

    override fun getLifeIndexes(): List<LifeIndex> {
        val lifeIndexes = ArrayList<LifeIndex>()
        val cityId = miWeather.forecast?.cityId
        miWeather.indexList?.forEach {
            lifeIndexes.add(LifeIndex(
                    cityId = cityId,
                    name = it.name,
                    index = it.index,
                    details = it.details
                    ))
        }
        return lifeIndexes
    }

    override fun getAirQualityLive(): AirQualityLive {
        val aqiEntity = miWeather.aqi
        val airQualityLive = AirQualityLive(
                cityId = miWeather.aqi?.cityId.toString(),
                aqi = aqiEntity?.aqi ?: 0,
                pm25 = aqiEntity?.pm25 ?: 0,
                pm10 = aqiEntity?.pm10 ?: 0,
                cityRank = "",
                quality = aqiEntity?.src
        )
        return airQualityLive
    }

    /**
     * 拆分天气
     *
     * @param weather 如：晴转多云
     * @return {"晴", "多云"}
     */
    private fun splitWeather(weather: String?): Array<String> {

        if (weather == null) {
            return arrayOf("", "")
        }
        return if (weather.contains("转")) {
            weather.split("转".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        } else {
            arrayOf(weather, weather)
        }
    }

    /**
     * 拆分气温
     *
     * @param temperature 如：5℃~-3℃
     * @return {5, 3}
     */
    private fun splitTemperature(temperature: String?): IntArray? {
        var temperature = temperature
        if (temperature != null && temperature.contains("~")) {
            if (temperature.contains("℃")) {
                temperature = temperature.replace("℃".toRegex(), "")
            }
            val temps = temperature.split("~".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return intArrayOf(Integer.parseInt(temps[0]), Integer.parseInt(temps[1]))
        } else {
            return null
        }
    }
}