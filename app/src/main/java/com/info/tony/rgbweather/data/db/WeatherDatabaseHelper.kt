package com.info.tony.rgbweather.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.info.tony.rgbweather.WeatherApplication
import com.info.tony.rgbweather.data.db.entities.rgblist.*
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.sql.SQLException

/**
 * Created by lvlu on 2018/3/9.
 */
class WeatherDatabaseHelper : OrmLiteSqliteOpenHelper {

    companion object {
        private val DATABASE_NAME = "weather.db"
        private val DATABASE_VERSION = 1
        fun instance(context: Context): WeatherDatabaseHelper = Inner.innerSingle

    }

    private object Inner{
        val innerSingle = WeatherDatabaseHelper(WeatherApplication.instance)
    }

    constructor(context: Context) : super(context, DATABASE_NAME, null, DATABASE_VERSION)


    override fun onCreate(database: SQLiteDatabase, connectionSource: ConnectionSource) {

        try {
            TableUtils.createTableIfNotExists<AirQualityLive>(connectionSource, AirQualityLive::class.java)
            TableUtils.createTableIfNotExists<WeatherForecast>(connectionSource, WeatherForecast::class.java)
            TableUtils.createTableIfNotExists<LifeIndex>(connectionSource, LifeIndex::class.java)
            TableUtils.createTableIfNotExists<WeatherLive>(connectionSource, WeatherLive::class.java)
            TableUtils.createTableIfNotExists<Weather>(connectionSource, Weather::class.java)

            val weatherTrigger = "CREATE TRIGGER trigger_delete AFTER DELETE " +
                    "ON Weather " +
                    "FOR EACH ROW " +
                    "BEGIN " +
                    "DELETE FROM AirQuality WHERE cityId = OLD.cityId; " +
                    "DELETE FROM WeatherLive WHERE cityId = OLD.cityId; " +
                    "DELETE FROM WeatherForecast WHERE cityId = OLD.cityId; " +
                    "DELETE FROM LifeIndex WHERE cityId = OLD.cityId; " +
                    "END;"
            database.execSQL(weatherTrigger)

        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    override fun onUpgrade(database: SQLiteDatabase, connectionSource: ConnectionSource, oldVersion: Int, newVersion: Int) {

        onCreate(database, connectionSource)
    }

    override fun close() {
        super.close()
        DaoManager.clearCache()
    }

    fun <D : Dao<T, *>, T> getWeatherDao(clazz: Class<T>): D? {
        try {
            return getDao(clazz)
        } catch (e: SQLException) {
            Log.e(this::class.simpleName, e.message)
        }

        return null
    }
}