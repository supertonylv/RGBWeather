package com.info.tony.rgbweather.data.http.entity.know

/**
 * Created by lvlu on 2018/3/9.
 */
data class KnowWeather(

        var cityId: String? = null,
        var basic: BasicEntity? = null,
        var aqi: AqiEntity? = null,
        var hoursForecast: List<HoursForecastEntity>? = null,
        var dailyForecast: List<DailyForecastEntity>? = null,
        var lifeIndex: List<LifeIndexEntity>? = null,
        var alarms: List<*>? = null

)

data class BasicEntity(
        /**
         * city : 北京
         * province : 北京市
         * temp : 3°
         * time : 2016-12-10 17:00:00
         * weather : 晴
         * weatherIcon : /public
         * img : 0
         */

        var city: String? = null,
        var province: String? = null,
        var temp: String? = null,
        var time: String? = null,
        var weather: String? = null,
        var weatherIcon: String? = null,
        var img: String? = null
)

data class AqiEntity(
        /**
         * aqi : 65
         * cityRank : 空气质量超过全国68%的城市或地区
         * pm10 : 37
         * pm25 : 59
         * quality : 良
         * advice : 气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。
         */

        var aqi: String? = null,
        var cityRank: String? = null,
        var pm10: String? = null,
        var pm25: String? = null,
        var quality: String? = null,
        var advice: String? = null
)

data class HoursForecastEntity(
        /**
         * temp : -1°
         * time : 2016-12-10 19:00:00
         * weather : 晴
         * weatherIcon : /public
         * img : 0
         */

        var temp: String? = null,
        var time: String? = null,
        var weather: String? = null,
        var weatherIcon: String? = null,
        var img: String? = null
)

data class DailyForecastEntity(

        /**
         * date : 2016-12-10
         * temp_range : -4~5°
         * weather : 晴
         * week : 今天
         * weatherIcon : /public
         * img : 0
         */

        var date: String? = null,
        var temp_range: String? = null,
        var weather: String? = null,
        var week: String? = null,
        var weatherIcon: String? = null,
        var img: String? = null
)

data class LifeIndexEntity(
        /**
         * name : 防晒
         * level : 较弱
         * content : 紫外线强度较弱，建议涂擦SPF在12-15之间，PA+的防晒护肤品。
         */

        var name: String? = null,
        var level: String? = null,
        var content: String? = null
)
