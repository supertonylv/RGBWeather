package com.info.tony.rgbweather.data.http.entity.mi

import com.alibaba.fastjson.annotation.JSONField

/**
 * Created by lvlu on 2018/3/9.
 */
data class MiRealTime(
        @JSONField(name = "SD")
        var humidity: String? = null,//湿度
        @JSONField(name = "WD")
        var wind: String? = null,//风向
        @JSONField(name = "WS")
        var windSpeed: String? = null,//风速
        @JSONField(name = "cityid")
        var cityId: String? = null,
        var temp: String? = null,//温度
        var time: String? = null,//发布时间
        var weather: String? = null//天气情况
)