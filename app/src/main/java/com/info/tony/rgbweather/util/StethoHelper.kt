package com.info.tony.rgbweather.util

import android.content.Context
import okhttp3.OkHttpClient

/**
 * Created by lvlu on 2018/3/12.
 */
interface StethoHelper {
    fun init(context: Context)

    fun addNetworkInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder?
}