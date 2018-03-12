package com.info.tony.rgbweather.data.db.entities.rgblist

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by lvlu on 2018/3/9.
 */
@DatabaseTable(tableName = "Weather")
data class Weather(

    @DatabaseField(columnName = "cityId", id = true,dataType = DataType.STRING)
    var cityId: String? = null,
    @DatabaseField(columnName = "cityName",dataType = DataType.STRING)
    var cityName: String? = null,
    @DatabaseField(columnName = "cityNameEn",dataType = DataType.STRING)
    var cityNameEn: String? = null,

    var weatherLive: WeatherLive? = null,

    var weatherForecasts: List<WeatherForecast>? = null,

    var airQualityLive: AirQualityLive? = null,

    var lifeIndexes: List<LifeIndex>? = null
)

