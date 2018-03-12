package com.info.tony.rgbweather.data.http.entity.mi

/**
 * Created by lvlu on 2018/3/9.
 */
data class MiToday(
        var cityCode: Int = 0,//城市ID
        var date: String? = null,//日期
        var humidityMax: Int = 0,//最大湿度
        var humidityMin: Int = 0,//最小湿度
        var precipitationMax: Int = 0,//最大降水量
        var precipitationMin: Int = 0,//最低降水量
        var tempMax: Int = 0,//最高温度
        var tempMin: Int = 0,//最低温度
        var weatherEnd: String? = null,
        var weatherStart: String? = null,
        var windDirectionEnd: String? = null,
        var windDirectionStart: String? = null,
        var windMax: Int = 0,//最大风力
        var windMin: Int = 0//最小风力
)