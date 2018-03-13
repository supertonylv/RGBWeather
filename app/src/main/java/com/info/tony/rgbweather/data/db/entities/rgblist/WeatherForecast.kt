package com.info.tony.rgbweather.data.db.entities.rgblist

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by lvlu on 2018/3/9.
 */
@DatabaseTable(tableName = "WeatherForecast")
data class WeatherForecast(
        @DatabaseField(columnName = "_id", generatedId = true, dataType = DataType.LONG)
        var id: Long = 0,//数据库自增长ID
        @DatabaseField(columnName = "cityId", dataType = DataType.STRING)
        var cityId: String? = null,
        @DatabaseField(columnName = "weather", dataType = DataType.STRING)
        var weather: String? = null,
        @DatabaseField(columnName = "weatherDay", dataType = DataType.STRING)
        var weatherDay: String? = null,
        @DatabaseField(columnName = "weatherNight", dataType = DataType.STRING)
        var weatherNight: String? = null,
        @DatabaseField(columnName = "tempMax", dataType = DataType.INTEGER)
        var tempMax: Int = 0,
        @DatabaseField(columnName = "tempMin", dataType = DataType.INTEGER)
        var tempMin: Int = 0,
        @DatabaseField(columnName = "wind", dataType = DataType.STRING)
        var wind: String? = null,
        @DatabaseField(columnName = "date", dataType = DataType.STRING)
        var date: String? = null,
        @DatabaseField(columnName = "week", dataType = DataType.STRING)
        var week: String? = null,//周一，周二，...

        @DatabaseField(columnName = "pop", dataType = DataType.STRING)
        var pop: String? = null,//降水概率(%)
        @DatabaseField(columnName = "uv", dataType = DataType.STRING)
        var uv: String? = null,//紫外线级别
        @DatabaseField(columnName = "visibility", dataType = DataType.STRING)
        var visibility: String? = null,//能见度(km)
        @DatabaseField(columnName = "humidity", dataType = DataType.STRING)
        var humidity: String? = null,//相对湿度(%)
        @DatabaseField(columnName = "pressure", dataType = DataType.STRING)
        var pressure: String? = null,//气压(hPa)
        @DatabaseField(columnName = "precipitation", dataType = DataType.STRING)
        var precipitation: String? = null,//降水量(mm)
        @DatabaseField(columnName = "sunrise", dataType = DataType.STRING)
        var sunrise: String? = null,//日出
        @DatabaseField(columnName = "sunset", dataType = DataType.STRING)
        var sunset: String? = null,//日落
        @DatabaseField(columnName = "moonrise", dataType = DataType.STRING)
        var moonrise: String? = null,//月升
        @DatabaseField(columnName = "moonset", dataType = DataType.STRING)
        var moonset: String? = null//月落
)