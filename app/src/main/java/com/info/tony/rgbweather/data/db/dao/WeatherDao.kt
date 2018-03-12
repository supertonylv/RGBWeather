package com.info.tony.rgbweather.data.db.dao

import android.content.Context
import com.info.tony.rgbweather.data.db.WeatherDatabaseHelper
import com.info.tony.rgbweather.data.db.entities.rgblist.*
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.misc.TransactionManager
import java.sql.SQLException
import java.util.concurrent.Callable
import javax.inject.Inject

/**
 * Created by lvlu on 2018/3/11.
 */
class WeatherDao {
    private val context: Context

    private val apiDaoOperation: Dao<AirQualityLive, String>?
    private val forecastDaoOperation: Dao<WeatherForecast, Long>?
    private val lifeIndexesDaoOperation: Dao<LifeIndex, Long>?
    private val realTimeDaoOperation: Dao<WeatherLive, String>?
    private val weatherDaoOperation: Dao<Weather, String>?

    @Inject
    constructor(context: Context) {
        this.context = context
        this.apiDaoOperation = WeatherDatabaseHelper.instance(context).getWeatherDao(AirQualityLive::class.java)
        this.forecastDaoOperation = WeatherDatabaseHelper.instance(context).getWeatherDao(WeatherForecast::class.java)
        this.lifeIndexesDaoOperation = WeatherDatabaseHelper.instance(context).getWeatherDao(LifeIndex::class.java)
        this.realTimeDaoOperation = WeatherDatabaseHelper.instance(context).getWeatherDao(WeatherLive::class.java)
        this.weatherDaoOperation = WeatherDatabaseHelper.instance(context).getWeatherDao(Weather::class.java)
    }

    @Throws(SQLException::class)
    fun queryWeather(cityId: String): Weather? {

        return TransactionManager.callInTransaction(WeatherDatabaseHelper.instance(context).getConnectionSource()) {
            val weather = weatherDaoOperation?.queryForId(cityId)
            if (weather != null) {
                weather.airQualityLive = apiDaoOperation?.queryForId(cityId)
                weather.weatherForecasts = forecastDaoOperation?.queryForEq("cityId", cityId)
                weather.lifeIndexes = lifeIndexesDaoOperation?.queryForEq("cityId", cityId)
                weather.weatherLive = realTimeDaoOperation?.queryForId(cityId)
            }
             weather
        }
    }

    @Throws(SQLException::class)
    fun insertOrUpdateWeather(weather: Weather) {

        TransactionManager.callInTransaction(WeatherDatabaseHelper.instance(context).getConnectionSource(), {
            if (weatherDaoOperation != null) {
            if (weatherDaoOperation?.idExists(weather.cityId)) {
                updateWeather(weather)
            } else {
                insertWeather(weather)
            }
            }
            null
        } as Callable<Void>)
    }

    @Throws(SQLException::class)
    fun deleteById(cityId: String) {

        weatherDaoOperation?.deleteById(cityId)
    }

    @Throws(SQLException::class)
    private fun delete(data: Weather) {

        weatherDaoOperation?.delete(data)
    }

    /**
     * 查询数据库中的所有已添加的城市
     *
     * @return 结果集中只包括城市信息，天气数据不在其中
     * @throws SQLException
     */
    @Throws(SQLException::class)
    fun queryAllSaveCity(): List<Weather>? {

        return TransactionManager.callInTransaction(WeatherDatabaseHelper.instance(context).getConnectionSource()) {

            val weatherList = weatherDaoOperation?.queryForAll()
            weatherList?.forEach {
                val cityId = it.cityId
                it.airQualityLive = apiDaoOperation?.queryForId(cityId)
                it.weatherForecasts = forecastDaoOperation?.queryForEq("cityId", cityId)
                it.lifeIndexes = lifeIndexesDaoOperation?.queryForEq("cityId", cityId)
                it.weatherLive = realTimeDaoOperation?.queryForId( cityId)

            }
            weatherList
        }
    }

    @Throws(SQLException::class)
    private fun insertWeather(weather: Weather) {

        weatherDaoOperation?.create(weather)
        apiDaoOperation?.create(weather.airQualityLive)
        weather.weatherForecasts?.forEach {
            forecastDaoOperation?.create(it)
        }
        weather.lifeIndexes?.forEach {
            lifeIndexesDaoOperation?.create(it)
        }
        realTimeDaoOperation?.create(weather.weatherLive)
    }

    @Throws(SQLException::class)
    private fun updateWeather(weather: Weather) {

        weatherDaoOperation?.update(weather)
        apiDaoOperation?.update(weather.airQualityLive)

        //先删除旧数据
        val forecastDeleteBuilder = forecastDaoOperation?.deleteBuilder()
        forecastDeleteBuilder?.where()?.eq("cityId", weather.cityId)
        val forecastPrepared = forecastDeleteBuilder?.prepare()
        forecastDaoOperation?.delete(forecastPrepared)
        //再插入新数据
        weather.weatherForecasts?.forEach {
            forecastDaoOperation?.create(it)
        }

        //先删除旧数据
        val lifeIndexDeleteBuilder = lifeIndexesDaoOperation?.deleteBuilder()
        lifeIndexDeleteBuilder?.where()?.eq("cityId", weather.cityId)
        val lifeIndexPrepared = lifeIndexDeleteBuilder?.prepare()
        lifeIndexesDaoOperation?.delete(lifeIndexPrepared)
        //再插入新数据
        weather.lifeIndexes?.forEach {
            lifeIndexesDaoOperation?.create(it)
        }
        realTimeDaoOperation?.update(weather.weatherLive)
    }
}