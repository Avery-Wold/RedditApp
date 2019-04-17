package com.example.averyw.redditapp.commons

import com.example.averyw.redditapp.commons.adapter.AdapterConstants
import com.example.averyw.redditapp.commons.adapter.ViewType

/**
 * Created by AveryW on 4/12/2019.
 */

data class RedditNews(
        val after: String,
        val before: String,
        val news: List<RedditNewsItem>)

data class RedditNewsItem(
        val author: String,
        val title: String,
        val numComments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String?
): ViewType {
    override fun getViewType() = AdapterConstants.NEWS
}