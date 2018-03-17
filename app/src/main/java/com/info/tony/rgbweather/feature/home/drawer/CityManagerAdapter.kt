package com.info.tony.rgbweather.feature.home.drawer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.info.tony.library.util.DateConvertUtils
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.base.BaseRecyclerViewAdapter
import com.info.tony.rgbweather.data.db.entities.rgblist.Weather
import com.info.tony.rgbweather.feature.WeatherDrawableUtil
import com.j256.ormlite.stmt.query.In
import kotterknife.bindView

/**
 * Created by lvlu on 2018/3/16.
 */
class CityManagerAdapter( val weatherList: MutableList<Weather>?) : BaseRecyclerViewAdapter<CityManagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_city_manager, parent, false)
        return ViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weatherList?.get(position)
        weather?.let { holder.bindData(weather,position,onWeatherItemClickListener,onDeleteClickListener) }
//        val removeWeather = weatherList[holder.adapterPosition]
//        weatherList.remove(removeWeather)
//        notifyItemRemoved(holder.adapterPosition)
//
//        if (onItemClickListener != null && onItemClickListener is OnCityManagerItemClickListener) {
//            (onItemClickListener as OnCityManagerItemClickListener).onDeleteClick(removeWeather.getCityId())
//        }
    }

    override fun getItemCount(): Int {
        return weatherList?.size?:0
    }

    private var onDeleteClickListener:((position:Int,weather:Weather) -> Unit)? = null
    private var onWeatherItemClickListener:((position:Int,weather:Weather) -> Unit)? = null

    fun setOnDeleteClickListener(listener:(position:Int,weather:Weather) -> Unit){
        this.onDeleteClickListener = listener
    }

    fun setOnCityClickListener(listener:(position:Int,weather:Weather) -> Unit){
        this.onWeatherItemClickListener = listener
    }

    class ViewHolder(itemView: View, adapter: CityManagerAdapter) : RecyclerView.ViewHolder(itemView) {

        val deleteButton: ImageButton by bindView(R.id.item_delete)
        val city: TextView by bindView(R.id.item_tv_city)
        val publishTime: TextView by bindView(R.id.item_tv_publish_time)
        val weather: TextView by bindView(R.id.item_tv_weather)
        val temp: TextView by bindView(R.id.item_tv_temp)
        val weatherImageView: ImageView by bindView(R.id.iv_weather)

        init {
            itemView.setOnClickListener { v -> adapter.onItemHolderClick(this@ViewHolder) }
        }

        fun bindData(w:Weather,position: Int,onWeatherItemClick: ((p:Int,weather:Weather) -> Unit)? ,onDeleteClickListener: ((position:Int,weather:Weather) -> Unit)?) {
            with(w) {
                city.text = cityName
                weather.text = weatherLive?.weather
                temp.text = StringBuilder().append(weatherForecasts?.get(0)?.tempMin).append("~").append(weatherForecasts?.get(0)?.tempMax).append("℃").toString()
                weatherImageView.setImageResource(WeatherDrawableUtil.getDrawableByWeather(w.weatherLive?.weather))
                publishTime.text = "发布于 " + DateConvertUtils.timeStampToDate(weatherLive?.time
                        ?: System.currentTimeMillis(), DateConvertUtils.DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM)
                onDeleteClickListener?.let {
                    deleteButton.setOnClickListener { onDeleteClickListener?.invoke(position,this) }
                    onWeatherItemClick?.let { itemView.setOnClickListener { v: View? -> kotlin.run {
                        onWeatherItemClick?.invoke(position,this)
                    }}  }
                }
            }
        }

    }

//    interface onDeleteClickListener{
//        operator fun invoke(position:Int)
//    }
//
//    interface OnCityManagerItemClickListener : AdapterView.OnItemClickListener {
//
//        fun onDeleteClick(cityId: String)
//    }

}