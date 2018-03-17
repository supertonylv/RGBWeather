package com.info.tony.rgbweather.di.component

import com.info.tony.rgbweather.di.module.ApplicationModule
import com.info.tony.rgbweather.feature.home.HomePagePresenter
import com.info.tony.rgbweather.feature.home.drawer.DrawerMenuPresenter
import com.info.tony.rgbweather.feature.selectcity.SelectCityPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by lvlu on 2018/3/14.
 */
@Singleton
@Component(modules = arrayOf( ApplicationModule::class))
interface PresenterComponent {
     fun inject(presenter: HomePagePresenter)
//
     fun inject(presenter: SelectCityPresenter)
//
     fun inject(presenter: DrawerMenuPresenter)
}