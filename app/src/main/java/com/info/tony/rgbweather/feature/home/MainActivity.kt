package com.info.tony.rgbweather.feature.home

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.base.BaseActivity
import com.info.tony.rgbweather.data.db.dao.WeatherDao
import com.info.tony.rgbweather.data.db.entities.rgblist.Weather
import com.info.tony.rgbweather.data.preference.PreferenceHelper
import com.info.tony.rgbweather.data.preference.WeatherSettings
import com.info.tony.rgbweather.data.repository.WeatherDataRepository
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import rx.*
import rx.Observable
import rx.Observer
import java.util.*
import rx.android.plugins.RxAndroidSchedulersHook
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action
import rx.functions.Action1
import rx.schedulers.Schedulers

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var drawerLayout:DrawerLayout?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = find<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = find<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            val cityId = PreferenceHelper.getSharedPreferences().getString(WeatherSettings.SETTINGS_CURRENT_CITY_ID.getId(),"");
            Log.e("xxxxx","cityId"+cityId)
            WeatherDataRepository.getWeather(this@MainActivity,cityId,WeatherDao(context = this@MainActivity),true)
                    ?.subscribeOn(Schedulers.io())
                     ?.observeOn(AndroidSchedulers.mainThread())
//                     ?.subscribe(Action1 { displayWeather(it) }, Action1 {  })
                    ?.subscribe(Action1<Weather>(){
                        displayWeather(it)
                    })

        }

        drawerLayout = find<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        val navView = find<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
    }

    fun displayWeather(weather: Weather){
        Log.e("xxx","weather = "+ weather.cityName)
    }

    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) ?: false) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item?.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
}

