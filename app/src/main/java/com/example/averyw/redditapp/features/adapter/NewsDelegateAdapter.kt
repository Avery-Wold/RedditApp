package com.example.averyw.redditapp.features.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.averyw.redditapp.R
import com.example.averyw.redditapp.commons.RedditNewsItem
import com.example.averyw.redditapp.commons.adapter.ViewType
import com.example.averyw.redditapp.commons.adapter.ViewTypeDelegateAdapter
import com.example.averyw.redditapp.commons.extensions.getFriendlyTime
import com.example.averyw.redditapp.commons.extensions.inflate
import com.example.averyw.redditapp.commons.extensions.loadImg
import kotlinx.android.synthetic.main.news_item.view.*

/**
 * Created by AveryW on 4/12/2019.
 */

class NewsDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as RedditNewsItem)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.news_item)) {

        private val imgThumbnail = itemView.img_thumbnail
        private val description = itemView.description
        private val author = itemView.author
        private val comments = itemView.comments
        private val time = itemView.time

        fun bind(item: RedditNewsItem) = with(itemView) {
//            Picasso.with(itemView.context).load(item.thumbnail).into(img_thumbnail)
            img_thumbnail.loadImg(item.thumbnail)
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()

//            super.itemView.setOnClickListener { viewActions.onItemSelected(item.url)}
        }
    }
}