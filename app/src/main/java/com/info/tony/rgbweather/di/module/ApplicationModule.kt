package com.info.tony.rgbweather.di.module

import android.content.Context
import com.info.tony.rgbweather.WeatherApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by lvlu on 2018/3/14.
 */
@Module
class ApplicationModule constructor(var context: Context){

//    @Singleton
    @Provides
    fun provideApplication(): WeatherApplication {

        return context.applicationContext as WeatherApplication
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}