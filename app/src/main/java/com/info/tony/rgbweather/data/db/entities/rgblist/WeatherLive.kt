package com.info.tony.rgbweather.data.db.entities.rgblist

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by lvlu on 2018/3/9.
 */
@DatabaseTable(tableName = "WeatherLive")
data class WeatherLive(
        @DatabaseField(columnName = "cityId", id = true,dataType = DataType.STRING)
        var cityId: String? = null,
        @DatabaseField(columnName = "weather",dataType = DataType.STRING)
         var weather: String? = null,//天气情况
        @DatabaseField(columnName = "temp",dataType = DataType.STRING)
         var temp: String? = null,//温度
        @DatabaseField(columnName = "humidity",dataType = DataType.STRING)
         var humidity: String? = null,//湿度
        @DatabaseField(columnName = "wind",dataType = DataType.STRING)
         var wind: String? = null,//风向
        @DatabaseField(columnName = "windSpeed",dataType = DataType.STRING)
         var windSpeed: String? = null,//风速
        @DatabaseField(columnName = "time",dataType = DataType.LONG)
         var time: Long = 0,//发布时间（时间戳）

        @DatabaseField(columnName = "windPower",dataType = DataType.STRING)
         var windPower: String? = null,//风力
        @DatabaseField(columnName = "rain",dataType = DataType.STRING)
         var rain: String? = null,//降雨量(mm)
        @DatabaseField(columnName = "feelsTemperature",dataType = DataType.STRING)
         var feelsTemperature: String? = null,//体感温度(℃)
        @DatabaseField(columnName = "airPressure",dataType = DataType.STRING)
         var airPressure: String? = null//气压(hPa)
)