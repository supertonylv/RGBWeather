package com.info.tony.rgbweather.data.http.configuration

import com.info.tony.rgbweather.data.http.ApiConstants


/**
 * Created by lvlu on 2018/3/10.
 */
open class ApiConfiguration1 private constructor(val dataSourceType:Int){

    constructor(builder : Builder) :this(builder.dataSourceType)

    companion object {
        fun build(init : Builder.() -> Unit) = Builder(init).build()
    }

    class Builder private constructor() {
        constructor(init:Builder.() -> Unit) : this() {
            init()
        }
        public var dataSourceType:Int = ApiConstants.WEATHER_DATA_SOURCE_TYPE_ENVIRONMENT_CLOUD

//        fun setDataSourceType(type:Int) {
//            dataSourceType = type;
//        }
        //DSL
        fun setDataSourceType(init:Builder.() -> Int) = apply {
                var temp = init();
                if(temp != ApiConstants.WEATHER_DATA_SOURCE_TYPE_ENVIRONMENT_CLOUD && temp != ApiConstants.WEATHER_DATA_SOURCE_TYPE_KNOW
                    && temp != ApiConstants.WEATHER_DATA_SOURCE_TYPE_MI) {
                    throw RuntimeException("the dataSourceType does not support")
                }
                dataSourceType = init()
        }

        fun build() = ApiConfiguration1(this);
    }
}