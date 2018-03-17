package com.info.tony.rgbweather.di.component

import android.content.Context
import com.info.tony.rgbweather.WeatherApplication
import com.info.tony.rgbweather.di.module.ApplicationModule
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton

/**
 * Created by lvlu on 2018/3/14.
 */
@Singleton
@Component(modules = arrayOf( ApplicationModule::class))
interface ApplicationComponent {
     fun getApplication(): WeatherApplication

     fun getContext(): Context
}