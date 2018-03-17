package com.info.tony.rgbweather.data.http.entity.mi

import com.google.gson.annotations.SerializedName

/**
 * Created by lvlu on 2018/3/9.
 */
data class MiWeather(
        var forecast: MiForecast? = null,
        @SerializedName("realtime")
        var realTime: MiRealTime? = null,
        var aqi: MiAQI? = null,
        @SerializedName("index")
        var indexList: List<MiIndex>? = null,
        var today: MiToday? = null,
        var yesterday: MiToday? = null
)