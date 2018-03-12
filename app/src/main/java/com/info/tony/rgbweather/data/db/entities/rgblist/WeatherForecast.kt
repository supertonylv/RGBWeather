package com.info.tony.rgbweather.data.db.entities.rgblist

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by lvlu on 2018/3/9.
 */
@DatabaseTable(tableName = "WeatherForecast")
data class WeatherForecast(
        @DatabaseField(columnName = "_id", generatedId = true,dataType = DataType.LONG)
        private var id: Long = 0,//数据库自增长ID
        @DatabaseField(columnName = "cityId",dataType = DataType.STRING)
        private var cityId: String? = null,
        @DatabaseField(columnName = "weather",dataType = DataType.STRING)
        private var weather: String? = null,
        @DatabaseField(columnName = "weatherDay",dataType = DataType.STRING)
        private var weatherDay: String? = null,
        @DatabaseField(columnName = "weatherNight",dataType = DataType.STRING)
        private var weatherNight: String? = null,
        @DatabaseField(columnName = "tempMax",dataType = DataType.INTEGER)
        private var tempMax: Int = 0,
        @DatabaseField(columnName = "tempMin",dataType = DataType.INTEGER)
        private var tempMin: Int = 0,
        @DatabaseField(columnName = "wind",dataType = DataType.STRING)
        private var wind: String? = null,
        @DatabaseField(columnName =  "date",dataType = DataType.STRING)
        private var date: String? = null,
        @DatabaseField(columnName = "week",dataType = DataType.STRING)
        private var week: String? = null ,//周一，周二，...

        @DatabaseField(columnName = "pop",dataType = DataType.STRING)
        private var pop: String? = null,//降水概率(%)
        @DatabaseField(columnName = "uv",dataType = DataType.STRING)
        private var uv: String? = null,//紫外线级别
        @DatabaseField(columnName = "visibility",dataType = DataType.STRING)
        private var visibility: String? = null,//能见度(km)
        @DatabaseField(columnName = "humidity",dataType = DataType.STRING)
        private var humidity: String? = null,//相对湿度(%)
        @DatabaseField(columnName = "pressure",dataType = DataType.STRING)
        private var pressure: String? = null,//气压(hPa)
        @DatabaseField(columnName = "precipitation",dataType = DataType.STRING)
        private var precipitation: String? = null,//降水量(mm)
        @DatabaseField(columnName = "sunrise",dataType = DataType.STRING)
        private var sunrise: String? = null,//日出
        @DatabaseField(columnName = "sunset",dataType = DataType.STRING)
        private var sunset: String? = null,//日落
        @DatabaseField(columnName = "moonrise",dataType = DataType.STRING)
        private var moonrise: String? = null,//月升
        @DatabaseField(columnName = "moonset",dataType = DataType.STRING)
        private var moonset: String? = null//月落
) {
}