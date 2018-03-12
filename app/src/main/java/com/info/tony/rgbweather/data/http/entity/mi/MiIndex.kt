package com.info.tony.rgbweather.data.http.entity.mi

/**
 * Created by lvlu on 2018/3/9.
 */
data class MiIndex(
        private var code: String? = null,
        var details: String? = null,
        var index: String? = null,
        var name: String? = null
        )