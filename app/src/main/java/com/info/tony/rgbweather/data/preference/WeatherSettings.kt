package com.info.tony.rgbweather.data.preference

/**
 * Created by lvlu on 2018/3/11.
 */
enum class WeatherSettings {
    /*默认配置项*/
    SETTINGS_FIRST_USE("first_use", java.lang.Boolean.TRUE),

    SETTINGS_CURRENT_CITY_ID("current_city_id", "");

    private var mId: String? = null
    private var mDefaultValue: Any? = null

    constructor(id: String, defaultValue: Any) {
        mId = id
        mDefaultValue = defaultValue
    }

    fun getId(): String? {
        return mId
    }

    fun getDefaultValue(): Any? {
        return this.mDefaultValue
    }

    companion object {

        fun fromId(id: String): WeatherSettings? {
            val values = values()
            for (value in values) {
                if (value.mId == id) {
                    return value
                }
            }
            return null
        }
    }
}