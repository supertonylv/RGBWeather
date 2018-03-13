package com.info.tony.rgbweather

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.info.tony.rgbweather.data.db.CityDatabaseHelper
import com.info.tony.rgbweather.data.preference.PreferenceHelper
import com.info.tony.rgbweather.data.preference.WeatherSettings
import com.info.tony.rgbweather.feature.home.MainActivity

import kotlinx.android.synthetic.main.activity_welcom.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class WelcomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcom)
        setSupportActionBar(toolbar)
        Log.e("WelcomActovity","onCreate()")
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        Observable.just<Unit>(initAppData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> go2MainActivity() }
    }

    private fun go2MainActivity(){
        startActivity(Intent(this,MainActivity::class.java))
    }

    private fun initAppData():Unit{
        PreferenceHelper.loadDefaults()
        if(PreferenceHelper.getSharedPreferences().getBoolean(WeatherSettings.SETTINGS_FIRST_USE.getId(),false)){
            PreferenceHelper.savePreference(WeatherSettings.SETTINGS_CURRENT_CITY_ID,"101020100" )
            PreferenceHelper.savePreference(WeatherSettings.SETTINGS_FIRST_USE,false )

        }
        CityDatabaseHelper.instance(this.applicationContext).importCityDB();
    }

}
