package com.info.tony.rgbweather.util.stetho

import android.content.Context
import com.info.tony.rgbweather.util.StethoHelper
import okhttp3.OkHttpClient

/**
 * Created by lvlu on 2018/3/12.
 */
class ReleaseStethoHelper: StethoHelper {
    override fun init(context: Context) {
    }

    override fun addNetworkInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder? {
        return null
    }
}