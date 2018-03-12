package com.info.tony.rgbweather.util

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by lvlu on 2018/3/11.
 */
open class NotNullSingleValueVar<T>():ReadWriteProperty<Any?,T> {

    private var value : T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("value not initialized!")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("value already initilized!")
    }

}