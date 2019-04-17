package com.example.averyw.redditapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by AveryW on 4/16/2019.
 */

interface  RedditApi {
    @GET("/search.json?q=anthemthegame")
    fun getTop(@Query("after") after: String,
               @Query("limit") limit: String)
            : Call<RedditNewsResponse>
}
