package com.example.averyw.redditapp.features.adapter

/**
 * Created by AveryW on 4/12/2019.
 */
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.averyw.redditapp.R
import com.example.averyw.redditapp.commons.adapter.ViewType
import com.example.averyw.redditapp.commons.adapter.ViewTypeDelegateAdapter
import com.example.averyw.redditapp.commons.extensions.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.news_item_loading)) {
    }
}