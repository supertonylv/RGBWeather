package com.info.tony.rgbweather.data.http.entity.mi

import com.google.gson.annotations.SerializedName


/**
 * Created by lvlu on 2018/3/9.
 */
data class MiRealTime(
        @SerializedName("SD")
        var humidity: String? = null,//湿度
        @SerializedName("WD")
        var wind: String? = null,//风向
        @SerializedName("WS")
        var windSpeed: String? = null,//风速
        @SerializedName("cityid")
        var cityId: String? = null,
        var temp: String? = null,//温度
        var time: String? = null,//发布时间
        var weather: String? = null//天气情况
)