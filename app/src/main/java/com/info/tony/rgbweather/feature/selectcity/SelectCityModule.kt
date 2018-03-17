package com.info.tony.rgbweather.feature.selectcity

import com.info.tony.rgbweather.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by lvlu on 2018/3/14.
 */
@Module
class SelectCityModule constructor(var view:SelectCityContract.View){

    @Provides
    @ActivityScoped
    fun provideSelectCityContractView():SelectCityContract.View = view
}