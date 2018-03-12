package com.info.tony.rgbweather.data.http.entity.mi

import com.alibaba.fastjson.annotation.JSONField

/**
 * Created by lvlu on 2018/3/9.
 */
data class MiWeather(
        var forecast: MiForecast? = null,
        @JSONField(name = "realtime")
        var realTime: MiRealTime? = null,
        var aqi: MiAQI? = null,
        @JSONField(name = "index")
        var indexList: List<MiIndex>? = null,
        var today: MiToday? = null,
        var yesterday: MiToday? = null
)