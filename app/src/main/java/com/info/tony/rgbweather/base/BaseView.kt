package com.info.tony.rgbweather.base

/**
 * Created by lvlu on 2018/3/9.
 */
open interface BaseView<T> {
    fun setPresenter(presenter:T)
}