package com.info.tony.rgbweather.data.http.entity.mi

import com.alibaba.fastjson.annotation.JSONField

/**
 * Created by lvlu on 2018/3/9.
 */
data class MiForecast(
        @JSONField(name = "city")
        var cityName: String? = null,
        @JSONField(name = "city_en")
        var cityEn: String? = null,
        @JSONField(name = "cityid")
        var cityId: String? = null,
        var date: String? = null,
        @JSONField(name = "date_y")
        var dateY: String? = null,
        var fl1: String? = null,
        var fl2: String? = null,
        var fl3: String? = null,
        var fl4: String? = null,
        var fl5: String? = null,
        var fl6: String? = null,

        var temp1: String? = null,//11℃~4℃
        var temp2: String? = null,
        var temp3: String? = null,
        var temp4: String? = null,
        var temp5: String? = null,
        var temp6: String? = null,
        var tempF1: String? = null,
        var tempF2: String? = null,
        var tempF3: String? = null,
        var tempF4: String? = null,
        var tempF5: String? = null,
        var tempF6: String? = null,
        var weather1: String? = null,
        var weather2: String? = null,
        var weather3: String? = null,
        var weather4: String? = null,
        var weather5: String? = null,
        var weather6: String? = null,
        var week: String? = null,
        var wind1: String? = null,
        var wind2: String? = null,
        var wind3: String? = null,
        var wind4: String? = null,
        var wind5: String? = null,
        var wind6: String? = null
)