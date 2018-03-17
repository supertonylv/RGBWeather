package com.info.tony.rgbweather.feature.home

import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.CollapsingToolbarLayout
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
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.info.tony.library.util.ActivityUtils
import com.info.tony.library.util.DateConvertUtils
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.WeatherApplication
import com.info.tony.rgbweather.base.BaseActivity
import com.info.tony.rgbweather.data.db.dao.WeatherDao
import com.info.tony.rgbweather.data.db.entities.rgblist.Weather
import com.info.tony.rgbweather.data.preference.PreferenceHelper
import com.info.tony.rgbweather.data.preference.WeatherSettings
import com.info.tony.rgbweather.data.repository.WeatherDataRepository
import com.info.tony.rgbweather.feature.WeatherDrawableUtil
import com.info.tony.rgbweather.feature.home.drawer.DrawerMenuFragment
import com.info.tony.rgbweather.feature.home.drawer.DrawerMenuPresenter
import com.info.tony.rgbweather.feature.selectcity.SelectCityActivity
import com.info.tony.rgbweather.feature.setting.AboutActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotterknife.bindView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import javax.inject.Inject

class MainActivity : BaseActivity(), HomePageFragment.OnFragmentInteractionListener, DrawerMenuFragment.OnSelectCity {


    val smartRefreshLayout: SmartRefreshLayout? by bindView(R.id.refresh_layout)

     val collapsingToolbarLayout: CollapsingToolbarLayout? by bindView(R.id.collapsing_toolbar)
     val toolbar: Toolbar? by bindView(R.id.toolbar)
     val drawerLayout: DrawerLayout? by bindView(R.id.drawer_layout)

    //基本天气信息
     val tempTextView: TextView? by bindView(R.id.temp_text_view)
     val weatherNameTextView: TextView? by bindView(R.id.weather_text_view)
     val realTimeTextView: TextView? by bindView(R.id.publish_time_text_view)
     val weatherImageView: ImageView? by bindView(R.id.weather_icon_image_view)

    @Inject
    lateinit var homePagePresenter: HomePagePresenter
    lateinit var drawerMenuPresenter: DrawerMenuPresenter

    private var currentCityId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        //设置 Header 为 Material风格
        val header = ClassicsHeader(this)
        header.setPrimaryColors(this.resources.getColor(R.color.colorPrimary), Color.WHITE)
        this.smartRefreshLayout?.setRefreshHeader(header)
        this.smartRefreshLayout?.setOnRefreshListener { refreshLayout -> homePagePresenter?.loadWeather(currentCityId?:"", true) }

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        assert(drawerLayout != null)
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        var homePageFragment: HomePageFragment? = supportFragmentManager.findFragmentById(R.id.fragment_container) as? HomePageFragment
        if (homePageFragment == null) {
            homePageFragment = HomePageFragment.newInstance()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, homePageFragment, R.id.fragment_container)
        }

        DaggerHomePageComponent.builder()
                .applicationComponent(WeatherApplication.instance.applicationComponent)
                .homePageModule(HomePageModule(homePageFragment))
                .build().inject(this)

        var drawerMenuFragment: DrawerMenuFragment? = supportFragmentManager.findFragmentById(R.id.fragment_container_drawer_menu) as? DrawerMenuFragment
        if (drawerMenuFragment == null) {
            drawerMenuFragment = DrawerMenuFragment.newInstance(1)
            ActivityUtils.addFragmentToActivity(supportFragmentManager, drawerMenuFragment, R.id.fragment_container_drawer_menu)
        }

        drawerMenuPresenter = DrawerMenuPresenter(this, drawerMenuFragment)
    }

    override fun onBackPressed() {
        val drawer = find(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.action_settings) {
            startActivity(Intent(this,AboutActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updatePageTitle(weather: Weather) {
        currentCityId = weather.cityId
        smartRefreshLayout?.finishRefresh()
        toolbar?.title = weather.cityName
        collapsingToolbarLayout?.title = weather.cityName

        tempTextView?.text = weather.weatherLive?.temp
        weatherNameTextView?.setText(weather.weatherLive?.weather)
        weatherImageView?.setImageResource(WeatherDrawableUtil.getDrawableByWeather(weather.weatherLive?.weather))
        realTimeTextView?.text = getString(R.string.string_publish_time) + DateConvertUtils.timeStampToDate(weather.weatherLive?.time?:System.currentTimeMillis(), DateConvertUtils.DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM)
    }

    override fun addOrUpdateCityListInDrawerMenu(weather: Weather) {
        drawerMenuPresenter.loadSavedCities()
    }

    override fun onSelect(cityId: String) {

        assert(drawerLayout != null)
        drawerLayout?.closeDrawer(GravityCompat.START)

        Handler().postDelayed({ homePagePresenter?.loadWeather(cityId, false) }, 250)
    }
}

