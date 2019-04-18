package com.example.averyw.redditapp.features

import com.example.averyw.redditapp.api.NewsAPI
import com.example.averyw.redditapp.api.NewsRestAPI
import com.example.averyw.redditapp.commons.RedditNews
import com.example.averyw.redditapp.commons.RedditNewsItem
import rx.Observable

/**
 * Created by AveryW on 4/16/2019.
 */

class NewsManager(private val api: NewsAPI = NewsRestAPI()) {

    fun getNews(after: String, limit: String = "10"): Observable<RedditNews> {
        return Observable.create {
            subscriber ->
            val callRespose = api.getNews(after, limit)
            val response = callRespose.execute()

            if(response.isSuccessful){
                val dataResponse = response.body().data
                val news = dataResponse.children.map {
                    val item = it.data
                    RedditNewsItem(item.author, item.title, item.num_comments,
                            item.created, item.thumbnail, item.url)
                }
                println(news)
                val redditNews = RedditNews(
                        dataResponse.after ?: "",
                        dataResponse.before ?: "",
                        news
                )
                subscriber.onNext(redditNews)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}