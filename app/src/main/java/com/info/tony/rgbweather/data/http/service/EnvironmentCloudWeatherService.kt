package com.info.tony.rgbweather.data.http.service

import com.info.tony.rgbweather.data.http.entity.envicloud.EnvironmentCloudCityAirLive
import com.info.tony.rgbweather.data.http.entity.envicloud.EnvironmentCloudForecast
import com.info.tony.rgbweather.data.http.entity.envicloud.EnvironmentCloudWeatherLive
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by lvlu on 2018/3/10.
 */
interface EnvironmentCloudWeatherService {
    /**
     * 获取指定城市的实时天气
     *
     *
     * API地址：http://service.envicloud.cn:8082/v2/weatherlive/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/101020100
     *
     * @param cityId 城市id
     * @return Observable
     */
    @GET("/v2/weatherlive/CM9IYMLUC2GXNTIXMJGZMZI0OTY2/{cityId}")
    fun getWeatherLive(@Path("cityId") cityId: String): Observable<EnvironmentCloudWeatherLive>

    /**
     * 获取指定城市7日天气预报
     *
     *
     * API地址：http://service.envicloud.cn:8082/v2/weatherforecast/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/101020100
     *
     * @param cityId 城市id
     * @return Observable
     */
    @GET("/v2/weatherforecast/CM9IYMLUC2GXNTIXMJGZMZI0OTY2/{cityId}")
    fun getWeatherForecast(@Path("cityId") cityId: String): Observable<EnvironmentCloudForecast>

    /**
     * 获取指定城市的实时空气质量
     *
     *
     * API地址：http://service.envicloud.cn:8082/v2/cityairlive/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/101020100
     *
     * @param cityId 城市id
     * @return Observable
     */
    @GET("/v2/cityairlive/CM9IYMLUC2GXNTIXMJGZMZI0OTY2/{cityId}")
    fun getAirLive(@Path("cityId") cityId: String): Observable<EnvironmentCloudCityAirLive>
}