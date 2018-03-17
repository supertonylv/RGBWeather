package com.info.tony.rgbweather.feature.selectcity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.base.BaseRecyclerViewAdapter
import com.info.tony.rgbweather.data.db.entities.City
import kotterknife.bindView
import java.util.ArrayList
import java.util.function.Consumer
import kotlinx.android.synthetic.main.item_city.*

/**
 * Created by lvlu on 2018/3/14.
 */
class CityListAdapter :BaseRecyclerViewAdapter<CityListAdapter.ViewHolder>,Filterable{
    lateinit var cities: MutableList<City>
    var mFilterData: List<City> //过滤后的数据

    private var filter: RecyclerViewFilter? = null
    var itemClick:OnItemClickListener

    constructor(cities: List<City>,itemClick:OnItemClickListener){
        this.cities = cities as MutableList<City>
        mFilterData = cities
        this.itemClick = itemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_city, parent, false)
        return ViewHolder(itemView,itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCity(mFilterData[position])
    }

    override fun getItemCount(): Int {
        return mFilterData.size
    }

    fun dataChange(cities: List<City>) {
        this.cities.clear()
        this.cities.addAll(cities)
        this.mFilterData = cities
        notifyDataSetChanged()
    }



    inner class ViewHolder(itemView: View,itemClick: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val cityNameTextView: TextView?  by bindView(R.id.city_name_text_view)

        fun bindCity(city: City) {
            with(city){
                cityNameTextView?.text = if(cityName != parent) (cityName+"-"+parent) else cityName
                itemView.setOnClickListener({itemClick(city)})
            }
        }
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = RecyclerViewFilter()
        }
        return filter as RecyclerViewFilter
    }

    fun filter(filterString:CharSequence){
        if (filter == null) {
            filter = RecyclerViewFilter()
        }
        filter?.filter(filterString)
    }



    inner class RecyclerViewFilter : Filter() {

        override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {

            //返回的results即为过滤后的ArrayList<City>
            val results = Filter.FilterResults()

            //无约束字符串则返回null
            if (charSequence == null || charSequence.length == 0) {
                results.values = null
                results.count = 0
            } else {
                val prefixString = charSequence.toString().toLowerCase()
                //新建Values存放过滤后的数据
//                val newValues = ArrayList<City>()
                val newValues = cities.filter {city: City ->  (city.cityName?.contains(prefixString)?:false
                        || city.cityNameEn?.contains(prefixString)?:false || city.parent?.contains(prefixString)?:false
                        || (city.root?.contains(prefixString))?:false) }
                results.values = newValues
                results.count = newValues.size
            }
            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
            if(filterResults.values != null) {
                mFilterData = filterResults.values as List<City>
                if (filterResults.count > 0) {
                    notifyDataSetChanged()//重绘当前可见区域
                } else {
                    mFilterData = cities
                    notifyDataSetChanged()//会重绘控件（还原到初始状态）
                }
            }

        }
    }

    public interface OnItemClickListener{
       operator fun invoke(city:City)
    }
}