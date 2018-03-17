package com.info.tony.rgbweather.feature.selectcity

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.info.tony.library.util.ActivityUtils
import com.info.tony.library.util.system.StatusBarHelper
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.WeatherApplication
import com.info.tony.rgbweather.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotterknife.bindView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SelectCityActivity : BaseActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)

    var selectCityFragment: SelectCityFragment? = null

    @Inject
    lateinit var selectCityPresenter: SelectCityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        selectCityFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)  as? SelectCityFragment
        if (selectCityFragment == null) {
            selectCityFragment = SelectCityFragment.newInstance()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, selectCityFragment as SelectCityFragment, R.id.fragment_container)
        }
        StatusBarUtil.setColor(this,resources.getColor(R.color.colorPrimaryDark))
        DaggerSelectCityComponent.builder()
                .applicationComponent(WeatherApplication.instance.applicationComponent)
                .selectCityModule(SelectCityModule(selectCityFragment as SelectCityFragment))
                .build().inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_city, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.action_search) {
            val searchView = MenuItemCompat.getActionView(item) as SearchView
            RxSearchView.queryTextChanges(searchView)
                    .map({ charSequence -> if (charSequence == null) null else charSequence!!.toString().trim() })
                    .throttleLast(100, TimeUnit.MILLISECONDS)
                    .debounce(100, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ searchText -> selectCityFragment?.cityListAdapter?.filter(searchText.toString()) })
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
