package com.info.tony.rgbweather.data.repository

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.info.tony.library.util.NetworkUtils
import com.info.tony.rgbweather.data.db.dao.WeatherDao
import com.info.tony.rgbweather.data.db.entities.adapter.CloudWeatherAdapter
import com.info.tony.rgbweather.data.db.entities.adapter.KnowWeatherAdapter
import com.info.tony.rgbweather.data.db.entities.adapter.MiWeatherAdapter
import com.info.tony.rgbweather.data.db.entities.rgblist.Weather
import com.info.tony.rgbweather.data.http.ApiClient
import com.info.tony.rgbweather.data.http.ApiConstants
import com.info.tony.rgbweather.data.http.entity.envicloud.EnvironmentCloudCityAirLive
import com.info.tony.rgbweather.data.http.entity.envicloud.EnvironmentCloudForecast
import com.info.tony.rgbweather.data.http.entity.envicloud.EnvironmentCloudWeatherLive
import rx.Observable
import rx.Single
import rx.exceptions.Exceptions
import rx.schedulers.Schedulers
import java.sql.SQLException
import java.util.function.Consumer

/**
 * Created by lvlu on 2018/3/12.
 */
object WeatherDataRepository {

    fun getWeather(context: Context, cityId: String, weatherDao: WeatherDao, refreshNow: Boolean): Observable<Weather>?{
        Log.e("xxx","getWeather")
        //从数据库获取天气数据
        val observableForGetWeatherFromDB = Observable.create<Weather> { subscriber ->
            try {
                val weather = weatherDao.queryWeather(cityId)
                Log.e("xxx","xxx weather")
                subscriber.onNext(weather)
                subscriber.onCompleted()
            } catch (e: SQLException) {
                throw Exceptions.propagate(e)
            }
        }
        Log.e("xxx","getWeather1")
        if (!NetworkUtils.isNetworkConnected(context))
            return observableForGetWeatherFromDB
        Log.e("xxx","getWeather2")
        //从服务端获取天气数据
        var observableForGetWeatherFromNetWork: Observable<Weather>? = null
        when (ApiClient.apiConfiguration?.dataSourceType) {
            ApiConstants.WEATHER_DATA_SOURCE_TYPE_KNOW -> observableForGetWeatherFromNetWork = ApiClient.weatherService?.getKnowWeather(cityId)
                    ?.map({ knowWeather -> KnowWeatherAdapter(knowWeather).getWeather() })
            ApiConstants.WEATHER_DATA_SOURCE_TYPE_MI -> observableForGetWeatherFromNetWork = ApiClient.weatherService?.getMiWeather(cityId)
                    ?.map({ miWeather -> MiWeatherAdapter(miWeather).getWeather() })
            ApiConstants.WEATHER_DATA_SOURCE_TYPE_ENVIRONMENT_CLOUD -> {

                val weatherLiveObservable = ApiClient.environmentCloudWeatherService?.getWeatherLive(cityId)
                val forecastObservable = ApiClient.environmentCloudWeatherService?.getWeatherForecast(cityId)
                val airLiveObservable = ApiClient.environmentCloudWeatherService?.getAirLive(cityId)

                Log.e("xxxx","weatherLive="+weatherLiveObservable+" forecastObservable="+forecastObservable+" airLiveObservable="+airLiveObservable)
//                observableForGetWeatherFromNetWork = Observable.combineLatest<EnvironmentCloudWeatherLive, EnvironmentCloudForecast, EnvironmentCloudCityAirLive, CloudWeatherAdapter>(weatherLiveObservable, forecastObservable, airLiveObservable
//                ,{ weatherLive, forecast, airLive -> CloudWeatherAdapter(weatherLive, forecast, airLive) })
//                        .map { t: CloudWeatherAdapter? ->  t?.getWeather()}
                var ob1:Observable<CloudWeatherAdapter> = Observable.combineLatest<EnvironmentCloudWeatherLive, EnvironmentCloudForecast, EnvironmentCloudCityAirLive, CloudWeatherAdapter>(weatherLiveObservable, forecastObservable, airLiveObservable
                        ,{ weatherLive, forecast, airLive -> CloudWeatherAdapter(weatherLive, forecast, airLive) })
                observableForGetWeatherFromNetWork = ob1.map { t ->  t.getWeather()}

            }
        }
        Log.e("xxx","getWeather3")
        assert(observableForGetWeatherFromNetWork != null)
//        observableForGetWeatherFromNetWork = observableForGetWeatherFromNetWork?.doOnNext { weather ->
//            Schedulers.io().createWorker().schedule {
//                try {
//                    weatherDao.insertOrUpdateWeather(weather)
//                } catch (e: SQLException) {
//                    throw Exceptions.propagate(e)
//                }
//            }
//        }

        Log.e("xxx","getWeather4 observableForGetWeatherFromNetWork="+observableForGetWeatherFromNetWork)
        return observableForGetWeatherFromNetWork
//        return Observable.concat<Weather>(observableForGetWeatherFromDB, observableForGetWeatherFromNetWork)
//                .filter { weather -> weather != null && !TextUtils.isEmpty(weather?.cityId) }
//                .distinct<Any> { weather -> weather.weatherLive?.time }
//                .takeUntil { weather -> !refreshNow && System.currentTimeMillis() - (weather.weatherLive?.time ?:0 )<= 15 * 60 * 1000 }
    }
}

