package com.info.tony.rgbweather.feature.home

import com.info.tony.rgbweather.di.component.ApplicationComponent
import com.info.tony.rgbweather.di.scope.ActivityScoped
import dagger.Component

/**
 * Created by lvlu on 2018/3/15.
 */
@ActivityScoped
@Component(modules = arrayOf(HomePageModule::class ),dependencies = arrayOf(ApplicationComponent::class) )
interface HomePageComponent {
    fun inject(mainActivity: MainActivity)
}