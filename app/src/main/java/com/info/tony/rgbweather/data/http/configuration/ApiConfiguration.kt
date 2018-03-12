package com.info.tony.rgbweather.data.http.configuration

/**
 * Created by lvlu on 2018/3/10.
 */
open class ApiConfiguration {

    private var dataSourceType:Int? = null;

    fun setDataSourceType(type:Int) {
        dataSourceType = type;
    }

    companion object {
        fun build(body:ApiConfiguration.() -> ApiConfiguration):ApiConfiguration {
            return with(ApiConfiguration()) {
                body()
            }
        }
    }
}