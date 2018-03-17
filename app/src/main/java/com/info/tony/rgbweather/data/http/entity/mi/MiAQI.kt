package com.info.tony.rgbweather.data.http.entity.mi

import com.google.gson.annotations.SerializedName


/**
 * Created by lvlu on 2018/3/9.
 */
data class MiAQI(
        @SerializedName("city")
        var cityName: String? = null,
        @SerializedName("city_id")
        var cityId: Int = 0,
        @SerializedName("pub_time")
        var publishTime: String? = null,
        var aqi: Int = 0,
        var pm25: Int = 0,
        var pm10: Int = 0,
        var so2: Int = 0,
        var no3: Int = 0,
        var src: String? = null,
        var spot: String? = null
)