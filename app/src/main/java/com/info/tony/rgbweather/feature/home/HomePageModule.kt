package com.info.tony.rgbweather.feature.home

import com.info.tony.rgbweather.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by lvlu on 2018/3/15.
 */
@Module
class HomePageModule constructor(var view:HomePageContract.View) {
    @ActivityScoped
    @Provides
    fun provideHomePageContractsView():HomePageContract.View = view
}