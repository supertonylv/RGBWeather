package com.info.tony.rgbweather.util

import kotlin.properties.ReadWriteProperty

/**
 * Created by lvlu on 2018/3/11.
 */
object DelegatesExt {
    fun <T> notNullSingleValue():ReadWriteProperty<Any?,T> = NotNullSingleValueVar()
}