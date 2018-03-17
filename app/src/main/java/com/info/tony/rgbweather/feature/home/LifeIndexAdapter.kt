package com.info.tony.rgbweather.feature.home

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.base.BaseRecyclerViewAdapter
import com.info.tony.rgbweather.data.db.entities.rgblist.LifeIndex
import com.info.tony.rgbweather.data.db.entities.rgblist.WeatherLive
import kotterknife.bindView

/**
 * Created by lvlu on 2018/3/15.
 */
class LifeIndexAdapter(val context: Context, private val indexList: List<LifeIndex>?) : BaseRecyclerViewAdapter<LifeIndexAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifeIndexAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_life_index, parent, false)
        return ViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: LifeIndexAdapter.ViewHolder, position: Int) {
        val index = indexList!![position]
        holder.bindData(index)
    }

    override fun getItemCount(): Int {
        return indexList?.size ?: 0
    }

    class ViewHolder(itemView: View, adapter: LifeIndexAdapter) : RecyclerView.ViewHolder(itemView) {

        val indexIconImageView: ImageView? by bindView<ImageView>(R.id.index_icon_image_view)
        val indexLevelTextView: TextView? by bindView<TextView>(R.id.index_level_text_view)
        val indexNameTextView: TextView? by bindView<TextView>(R.id.index_name_text_view)

        init {
            itemView.setOnClickListener { v -> adapter.onItemHolderClick(this@ViewHolder) }
        }

        fun bindData(lifeIndex: LifeIndex) {
            with(lifeIndex) {
                indexIconImageView?.setImageDrawable(getIndexDrawable(itemView.context, name.toString()))
                indexLevelTextView?.setText(index)
                indexNameTextView?.setText(name)
            }
        }

        private fun getIndexDrawable(context: Context, indexName: String): Drawable {


            var colorResourceId = R.drawable.ic_index_sunscreen
            if (indexName.contains("防晒")) {
                colorResourceId = R.drawable.ic_index_sunscreen
            } else if (indexName.contains("穿衣")) {
                colorResourceId = R.drawable.ic_index_dress
            } else if (indexName.contains("运动")) {
                colorResourceId = R.drawable.ic_index_sport
            } else if (indexName.contains("逛街")) {
                colorResourceId = R.drawable.ic_index_shopping
            } else if (indexName.contains("晾晒")) {
                colorResourceId = R.drawable.ic_index_sun_cure
            } else if (indexName.contains("洗车")) {
                colorResourceId = R.drawable.ic_index_car_wash
            } else if (indexName.contains("感冒")) {
                colorResourceId = R.drawable.ic_index_clod
            } else if (indexName.contains("广场舞")) {
                colorResourceId = R.drawable.ic_index_dance
            } else if (indexName.contains("质量")) {
                colorResourceId = R.drawable.ic_aq1
            }else if (indexName.contains("舒适度")) {
                colorResourceId = R.drawable.ic_if_heating
            }else if (indexName.contains("旅游")) {
                colorResourceId = R.drawable.ic__travel
            }else if (indexName.contains("紫外线")) {
                colorResourceId = R.drawable.ic_sunny_ray
            }
            return context.theme.getDrawable(colorResourceId)
        }
    }

}