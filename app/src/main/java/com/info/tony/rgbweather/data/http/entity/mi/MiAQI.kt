package com.info.tony.rgbweather.data.http.entity.mi

import com.alibaba.fastjson.annotation.JSONField

/**
 * Created by lvlu on 2018/3/9.
 */
data class MiAQI(
        @JSONField(name = "city")
        var cityName: String? = null,
        @JSONField(name = "city_id")
        var cityId: Int = 0,
        @JSONField(name = "pub_time")
        var publishTime: String? = null,
        var aqi: Int = 0,
        var pm25: Int = 0,
        var pm10: Int = 0,
        var so2: Int = 0,
        var no3: Int = 0,
        var src: String? = null,
        var spot: String? = null
)