package com.info.tony.rgbweather.feature.home

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
import com.info.tony.rgbweather.data.WeatherDeatail
import kotterknife.bindView

/**
 * Created by lvlu on 2018/3/15.
 */
class DetailAdapter(private val details: List<WeatherDeatail>?) : BaseRecyclerViewAdapter<DetailAdapter.ViewHolder>() {

//    val itemCount: Int
//        get() = details?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail, parent, false)
        return ViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: DetailAdapter.ViewHolder, position: Int) {
        val detail = details?.get(position)
        holder.detailIconImageView.setImageResource(detail?.iconResourceId?:0)
        holder.detailKeyTextView.setText(detail?.key)
        holder.detailValueTextView.setText(detail?.value)
    }

    override fun getItemCount(): Int {
        return details?.size ?:0
    }

    class ViewHolder(itemView: View, adapter: DetailAdapter) : RecyclerView.ViewHolder(itemView) {

        val detailIconImageView: ImageView by bindView<ImageView>(R.id.detail_icon_image_view)
        val detailKeyTextView: TextView by bindView<TextView>(R.id.detail_key_text_view)
        val detailValueTextView: TextView by bindView<TextView>(R.id.detail_value_text_view)

        init {
            itemView.setOnClickListener { v -> adapter.onItemHolderClick(this@ViewHolder) }
        }
    }
}