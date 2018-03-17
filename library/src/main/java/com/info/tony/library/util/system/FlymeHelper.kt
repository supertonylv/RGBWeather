package com.info.tony.library.util.system

import android.app.Activity
import android.view.WindowManager

/**
 * Created by lvlu on 2018/3/13.
 */
object FlymeHelper:SystemHelper {
    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为 Flyme 用户
     *
     * @param isFontColorDark 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回 true
     */
    override fun setStatusBarLightMode(activity: Activity, isFontColorDark: Boolean): Boolean {
        val window = activity.window
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val flymeFlags = WindowManager.LayoutParams::class.java
                        .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                flymeFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = flymeFlags.getInt(lp)
                if (isFontColorDark) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                flymeFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return result
    }
}