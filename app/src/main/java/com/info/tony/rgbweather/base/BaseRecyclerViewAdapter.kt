package com.info.tony.rgbweather.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AdapterView

/**
 * Created by lvlu on 2018/3/9.
 */
open abstract class BaseRecyclerViewAdapter<T : RecyclerView.ViewHolder> :RecyclerView.Adapter<T>() {

    protected var onItemClickListener: AdapterView.OnItemClickListener? = null

    fun setOnItemClickListenser(onItemClickListener: AdapterView.OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun onItemHolderClick(vh:RecyclerView.ViewHolder?) {
        onItemClickListener?.onItemClick(null,vh?.itemView,vh?.adapterPosition?:0,vh?.itemId?:0)
    }

}