package com.info.tony.rgbweather.data.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by lvlu on 2018/3/10.
 */
class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? = null
}