package com.info.tony.rgbweather.data.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.info.tony.rgbweather.WeatherApplication
import com.info.tony.rgbweather.data.http.configuration.ApiConfiguration1
import java.io.InvalidClassException

/**
 * Created by lvlu on 2018/3/11.
 */
object PreferenceHelper {
    private val TAG = "Preferences"

    private val SETTINGS_FILENAME = "WeatherApplication"

    private var CONFIGURATION_LISTENERS:MutableList<ConfigurationListener> = emptyList<ConfigurationListener>().toMutableList()

    fun loadDefaults() {
        //设置SharedPreferences默认值
        try {
            val defaultPrefs = HashMap<WeatherSettings, Any?>()
            val values = WeatherSettings.values()
            values.forEach  {
                defaultPrefs.put(it,it.getDefaultValue())
            }
            savePreferences(defaultPrefs, true)
        } catch (ex: Exception) {
            Log.e(TAG, "Save default settings fails", ex)
        }

    }

    fun addConfigurationListener(listener: ConfigurationListener) {
        CONFIGURATION_LISTENERS.add(listener)
    }

    fun removeConfigurationListener(listener: ConfigurationListener) {
        CONFIGURATION_LISTENERS!!.remove(listener)
    }

    fun getSharedPreferences(): SharedPreferences {
        return WeatherApplication.instance.getSharedPreferences(
                SETTINGS_FILENAME, Context.MODE_PRIVATE)
    }

    @Throws(InvalidClassException::class)
    fun savePreference(pref: WeatherSettings, value: Any) {
        val prefs = HashMap<WeatherSettings, Any>()
        prefs[pref] = value
        savePreferences(prefs, false)
    }

    @Throws(InvalidClassException::class)
    fun savePreferences(prefs: Map<WeatherSettings, Any>) {

        savePreferences(prefs, false)
    }

    @Throws(InvalidClassException::class)
    private fun savePreferences(prefs: Map<WeatherSettings, Any?>, noSaveIfExists: Boolean) {

        val sp = getSharedPreferences()
        val editor = sp.edit()

        for (pref in prefs.keys) {

            val value = prefs[pref]

            if (noSaveIfExists && sp.contains(pref.getId())) {
                continue
            }

            if (value is Boolean && pref.getDefaultValue() is Boolean) {
                editor.putBoolean(pref.getId(), value)
            } else if (value is String && pref.getDefaultValue() is String) {
                editor.putString(pref.getId(), value)
            } else if (value is Set<*> && pref.getDefaultValue() is Set<*>) {
                editor.putStringSet(pref.getId(), value as Set<String>)
            } else if (value is Int && pref.getDefaultValue() is Int) {
                editor.putInt(pref.getId(), value)
            } else if (value is Float && pref.getDefaultValue() is Float) {
                editor.putFloat(pref.getId(), value)
            } else if (value is Long && pref.getDefaultValue() is Long) {
                editor.putLong(pref.getId(), value)
            } else {
                //The object is not of the appropriate type
                val msg = String.format("%s: %s", pref.getId(), value!!::class.simpleName)
                Log.e(TAG, String.format("Configuration error. InvalidClassException: %s", msg))
                throw InvalidClassException(msg)
            }
        }

        editor.apply()

        if (CONFIGURATION_LISTENERS != null && CONFIGURATION_LISTENERS.size > 0) {
            for (pref in prefs.keys) {
                val value = prefs[pref]
                for (listener in CONFIGURATION_LISTENERS) {
                    listener.onConfigurationChanged(pref, value)
                }
            }
        }
    }
}