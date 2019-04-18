package com.example.averyw.redditapp.api

import retrofit2.Call

/**
 * Created by AveryW on 4/18/2019.
 */

interface NewsAPI {
    fun getNews(after: String, limit: String): Call<RedditNewsResponse>
}