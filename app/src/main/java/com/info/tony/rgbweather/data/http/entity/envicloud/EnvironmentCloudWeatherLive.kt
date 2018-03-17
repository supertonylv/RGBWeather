package com.info.tony.rgbweather.data.http.entity.envicloud

import com.google.gson.annotations.SerializedName


/**
 * 天气实况
 * Created by lvlu on 2018/3/9.
 */
open class EnvironmentCloudWeatherLive (


    /**
     * airpressure : 1016.0
     * rain : 0.0
     * windpower : 微风
     * rcode : 200
     * feelst : 17.7
     * citycode : 101020100
     * rdesc : Success
     * winddirect : 西北风
     * temperature : 17.8
     * humidity : 50.0
     * windspeed : 0.9
     * updatetime : 2017-02-16 14:06
     * phenomena : 阵雨
     */

    @SerializedName("rcode")
    var requestCode: Int = 0,//结果吗

    @SerializedName("rdesc")
    var requestDesc: String? = null,//结果描述

    @SerializedName("updatetime")
    var updateTime: String? = null,//更新时间

    var phenomena: String? = null,//天气现象

    var temperature: String? = null,//气温(℃)

    @SerializedName("feelst")
    var feelsTemperature: String? = null,//体感温度(℃)

    @SerializedName("airpressure")
    var airPressure: String? = null,//气压(hPa)

    var humidity: String? = null,//相对湿度(%)

    var rain: String? = null,//降雨量(mm)

    @SerializedName("winddirect")
    var windDirect: String? = null,//风向

    @SerializedName("windpower")
    var windPower: String? = null,//风力

    @SerializedName("windspeed")
    var windSpeed: String? = null,//风速(m/s)

    @SerializedName("citycode")
    var cityId: String? = null//城市ID
)