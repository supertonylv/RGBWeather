package com.info.tony.rgbweather.feature.selectcity

import com.info.tony.rgbweather.di.component.ApplicationComponent
import com.info.tony.rgbweather.di.scope.ActivityScoped
import dagger.Component

/**
 * Created by lvlu on 2018/3/14.
 */
@ActivityScoped
@Component(modules = arrayOf(SelectCityModule::class),dependencies = arrayOf(ApplicationComponent::class))
interface SelectCityComponent {
    fun inject(selectCityActivity:SelectCityActivity)
}