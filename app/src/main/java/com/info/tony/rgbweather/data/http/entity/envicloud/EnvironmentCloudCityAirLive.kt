package com.info.tony.rgbweather.data.http.entity.envicloud

import com.alibaba.fastjson.annotation.JSONField

/**
 * 城市实时空气质量
 * Created by lvlu on 2018/3/9.
 */
data class EnvironmentCloudCityAirLive(
        @JSONField(name = "rcode")
         var requestCode: Int = 0,//结果吗

        @JSONField(name = "rdesc")
         var requestDesc: String? = null,//结果描述

        @JSONField(name = "citycode")
         var cityId: String? = null,//城市ID

         var time: String? = null,//时间(yyyyMMddHH)

        @JSONField(name = "AQI")
         var aqi: String? = null,//空气质量指数

        @JSONField(name = "PM25")
         var pm25: String? = null,//PM2.5浓度(μg/m3)

        @JSONField(name = "PM10")
         var pm10: String? = null,//PM10浓度(μg/m3)

        @JSONField(name = "CO")
         var co: String? = null,//一氧化碳浓度(mg/m3)

        @JSONField(name = "SO2")
         var so2: String? = null,//二氧化硫浓度(μg/m3)

        @JSONField(name = "NO2")
         var no2: String? = null,//二氧化氮浓度(μg/m3)

         var o3: String? = null,//臭氧浓度(μg/m3)

         var primary: String? = null//首要污染物

)