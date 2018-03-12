package com.info.tony.rgbweather.data.http

import com.info.tony.rgbweather.BuildConfig
import com.info.tony.rgbweather.data.db.entities.rgblist.Weather
import com.info.tony.rgbweather.data.http.configuration.ApiConfiguration
import com.info.tony.rgbweather.data.http.configuration.ApiConfiguration1
import com.info.tony.rgbweather.data.http.service.EnvironmentCloudWeatherService
import com.info.tony.rgbweather.data.http.service.WeatherService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.fastjson.FastJsonConverterFactory
import kotlin.reflect.KClass

/**
 * Created by lvlu on 2018/3/9.
 */
object ApiClient {

    public var weatherService: WeatherService? = null;
    public var environmentCloudWeatherService: EnvironmentCloudWeatherService? = null

    public var apiConfiguration: ApiConfiguration1? = null;

    public fun init(configuration: ApiConfiguration1?) {
        apiConfiguration = configuration;
        when(configuration?.dataSourceType) {
            ApiConstants.WEATHER_DATA_SOURCE_TYPE_KNOW -> initWeatherService(ApiConstants.KNOW_WEATHER_API_HOST,WeatherService::class.java)
            ApiConstants.WEATHER_DATA_SOURCE_TYPE_ENVIRONMENT_CLOUD -> initWeatherService(ApiConstants.ENVIRONMENT_CLOUD_WEATHER_API_HOST,EnvironmentCloudWeatherService::class.java)
            ApiConstants.WEATHER_DATA_SOURCE_TYPE_MI -> {initWeatherService(ApiConstants.MI_WEATHER_API_HOST,WeatherService::class.java)}
            else -> throw RuntimeException("ApiClient dataSourceType does not support!")

        }
    }

    private fun <T> initWeatherService(baseUrl: String, clazz: Class<T>): T {


        val builder = OkHttpClient().newBuilder()
//            if (BuildConfig.DEBUG) {
//                val httpLoggingInterceptor = HttpLoggingInterceptor()
//                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//                builder.addInterceptor(httpLoggingInterceptor)
//                //            builder.addNetworkInterceptor(new StethoInterceptor());
//                BuildConfig.STETHO.addNetworkInterceptor(builder)
//            }
        val client = builder.build()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()

        return retrofit.create(clazz)
    }
}