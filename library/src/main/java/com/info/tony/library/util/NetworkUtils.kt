package com.info.tony.library.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by lvlu on 2018/3/12.
 */
object NetworkUtils {
    @SuppressLint("MissingPermission")
            /**
     * 判断网络连接是否可用
     */
    fun isNetworkConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (manager != null) {
            val networkinfo = manager.activeNetworkInfo
            if (networkinfo != null && networkinfo.isConnected && networkinfo.isAvailable) {
                return true
            }
        }
        return false
    }
}