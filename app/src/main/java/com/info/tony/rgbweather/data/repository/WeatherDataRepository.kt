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
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.exceptions.Exceptions
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.sql.SQLException
import java.util.function.BiFunction
import java.util.function.Consumer

/**
 * Created by lvlu on 2018/3/12.
 */
object WeatherDataRepository {

    fun getWeather(context: Context, cityId: String, weatherDao: WeatherDao, refreshNow: Boolean): Observable<Weather>? {
        Log.e("xxx", "getWeather")
        //从数据库获取天气数据
        val observableForGetWeatherFromDB = Observable.create<Weather> { subscriber ->
            try {
                val weather = weatherDao.queryWeather(cityId)
                if(weather != null) {
                    subscriber.onNext(weather)
                }
                subscriber.onComplete()
            } catch (e: SQLException) {
                throw Exceptions.propagate(e)
            }
        }
        if (!NetworkUtils.isNetworkConnected(context))
            return observableForGetWeatherFromDB
        //从服务端获取天气数据
        var observableForGetWeatherFromNetWork: Observable<Weather>? =
                when (ApiClient.apiConfiguration?.dataSourceType) {
                    ApiConstants.WEATHER_DATA_SOURCE_TYPE_KNOW -> return ApiClient.weatherService?.getKnowWeather(cityId)
                            ?.map({ knowWeather -> KnowWeatherAdapter(knowWeather).getWeather() })
                    ApiConstants.WEATHER_DATA_SOURCE_TYPE_MI -> return ApiClient.weatherService?.getMiWeather(cityId)
                            ?.map({ miWeather -> MiWeatherAdapter(miWeather).getWeather() })
                    ApiConstants.WEATHER_DATA_SOURCE_TYPE_ENVIRONMENT_CLOUD -> {

                        val weatherLiveObservable = ApiClient.environmentCloudWeatherService?.getWeatherLive(cityId)
                        val forecastObservable = ApiClient.environmentCloudWeatherService?.getWeatherForecast(cityId)
                        val airLiveObservable = ApiClient.environmentCloudWeatherService?.getAirLive(cityId)
                        Observable.combineLatest(weatherLiveObservable,forecastObservable,airLiveObservable,
                                io.reactivex.functions.Function3<EnvironmentCloudWeatherLive, EnvironmentCloudForecast,EnvironmentCloudCityAirLive, CloudWeatherAdapter> { t1, t2,t3 -> CloudWeatherAdapter(t1, t2, t3) })
                                .map { t:CloudWeatherAdapter? -> t?.getWeather() }
                    }
                    else -> {
                        return null
                    }
                }
        Log.e("xxx", "getWeather3")
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

        observableForGetWeatherFromNetWork = observableForGetWeatherFromNetWork?.observeOn(Schedulers.io())
                ?.doOnNext { weather -> weatherDao.insertOrUpdateWeather(weather) }
//        Log.e("xxx", "getWeather4 observableForGetWeatherFromNetWork=" + observableForGetWeatherFromNetWork)
//        return observableForGetWeatherFromDB
        return Observable.concat<Weather>(observableForGetWeatherFromDB, observableForGetWeatherFromNetWork)
                .filter { weather -> weather != null && !TextUtils.isEmpty(weather.cityId) }
                .distinct<Any> { weather -> weather.weatherLive?.time }
                .takeUntil { weather ->
                    !refreshNow && System.currentTimeMillis() - (weather.weatherLive?.time
                            ?: 0) <= 15 * 60 * 1000
                }.observeOn(Schedulers.io())
    }
}

