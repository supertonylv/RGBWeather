package com.info.tony.rgbweather.feature.home.drawer

import com.info.tony.rgbweather.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by lvlu on 2018/3/16.
 */
@Module
class DrawerMenuModule(private val view: DrawerContract.View) {

    @Provides
    @ActivityScoped
    internal fun provideCityManagerContactView(): DrawerContract.View {
        return view
    }
}