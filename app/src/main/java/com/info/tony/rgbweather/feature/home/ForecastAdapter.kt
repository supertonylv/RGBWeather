package com.info.tony.rgbweather.feature.home

import android.annotation.SuppressLint
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.base.BaseRecyclerViewAdapter
import com.info.tony.rgbweather.data.db.entities.rgblist.WeatherForecast
import com.info.tony.rgbweather.feature.WeatherDrawableUtil
import kotterknife.bindView

/**
 * Created by lvlu on 2018/3/15.
 */
class ForecastAdapter(val weatherForecasts: List<WeatherForecast>?) : BaseRecyclerViewAdapter<ForecastAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(itemView, this)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
        val weatherForecast = weatherForecasts!![position]
        holder.bindData(weatherForecast)
    }

    override fun getItemCount(): Int {
        return weatherForecasts?.size ?: 0
    }

    class ViewHolder(itemView: View, adapter: ForecastAdapter) : RecyclerView.ViewHolder(itemView) {

        val weekTextView: TextView?  by bindView<TextView>(R.id.week_text_view)
        val dateTextView: TextView?  by bindView<TextView>(R.id.date_text_view)
        val weatherIconImageView: ImageView? by bindView<ImageView>(R.id.weather_icon_image_view)
        val weatherTextView: TextView? by bindView<TextView>(R.id.weather_text_view)
        val tempMaxTextView: TextView? by bindView<TextView>(R.id.temp_max_text_view)
        val tempMinTextView: TextView? by bindView<TextView>(R.id.temp_min_text_view)

        init {
            itemView.setOnClickListener { v -> adapter.onItemHolderClick(this@ViewHolder) }
        }

        fun bindData(weatherForecast: WeatherForecast):Unit {
//            itemView.setOnClickListener { v: View? ->  {}}
            with(weatherForecast){
                weekTextView?.text = week
                dateTextView?.text = date
                weatherIconImageView?.setImageResource(WeatherDrawableUtil.getDrawableByWeather(if(weather == null) weatherDay else weather))
                weatherTextView?.text =
                        if (TextUtils.isEmpty(weather)) {
                            if (weatherDay.equals(weatherNight))
                                weatherDay
                            else
                                weatherDay + "转" + weatherNight
                        } else {
                            weather
                        }
                tempMaxTextView?.text = weatherForecast.tempMax.toString() + "°"
                tempMinTextView?.text = weatherForecast.tempMin.toString() + "°"
            }
        }
    }
}