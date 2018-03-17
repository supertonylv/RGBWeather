package com.info.tony.library.util.system

import android.app.Activity

/**
 * Created by lvlu on 2018/3/13.
 */
interface SystemHelper {
    fun setStatusBarLightMode(activity: Activity, isFontColorDark: Boolean): Boolean
}