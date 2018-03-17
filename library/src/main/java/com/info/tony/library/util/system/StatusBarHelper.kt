package com.info.tony.library.util.system

import android.app.Activity
import android.os.Build
import android.support.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by lvlu on 2018/3/13.
 */
object StatusBarHelper {
    const val MIUI = 1
    const val FLYME = 2
    const val ANDROID_M = 3
    const val OTHER = 4

    @IntDef(MIUI.toLong(), FLYME.toLong(), ANDROID_M.toLong(), OTHER.toLong())
    @Retention(RetentionPolicy.SOURCE)
    internal annotation class SystemType

    fun statusBarLightMode(activity: Activity): Int {
        return statusMode(activity, true)
    }

    fun statusBarDarkMode(activity: Activity): Int {
        return statusMode(activity, false)
    }

    private fun statusMode(activity: Activity, isFontColorDark: Boolean): Int {
        @SystemType var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUIHelper.setStatusBarLightMode(activity, isFontColorDark)) {
                result = MIUI
            } else if (FlymeHelper.setStatusBarLightMode(activity, isFontColorDark)) {
                result = FLYME
            }
            //            else if (new AndroidMHelper().setStatusBarLightMode(activity, isFontColorDark)) {
            //                result = ANDROID_M;
            //            }
        }
        return result
    }


    fun statusBarLightMode(activity: Activity, @SystemType type: Int) {
        statusBarMode(activity, type, true)

    }

    fun statusBarDarkMode(activity: Activity, @SystemType type: Int) {
        statusBarMode(activity, type, false)
    }

    private fun statusBarMode(activity: Activity, @SystemType type: Int, isFontColorDark: Boolean) {
        if (type == MIUI) {
            MIUIHelper.setStatusBarLightMode(activity, isFontColorDark)
        } else if (type == FLYME) {
            FlymeHelper.setStatusBarLightMode(activity, isFontColorDark)
        }
        //        else if (type == ANDROID_M) {
        //            new AndroidMHelper().setStatusBarLightMode(activity, isFontColorDark);
        //        }
    }
}