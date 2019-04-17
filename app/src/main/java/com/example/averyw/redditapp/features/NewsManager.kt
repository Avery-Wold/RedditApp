package com.example.averyw.redditapp.features

import com.example.averyw.redditapp.api.RestAPI
import com.example.averyw.redditapp.commons.RedditNewsItem
import rx.Observable

/**
 * Created by AveryW on 4/16/2019.
 */

class NewsManager(private val api: RestAPI = RestAPI()) {

    fun getNews(limit: String = "10"): Observable<List<RedditNewsItem>> {
        return Observable.create {
            subscriber ->
            val callRespose = api.getNews("", limit)
            val response = callRespose.execute()

            if(response.isSuccessful){
                val news = response.body().data.children.map {
                    val item = it.data
                    RedditNewsItem(item.author, item.title, item.num_comments,
                            item.created, item.thumbnail, item.url)
                }
                subscriber.onNext(news)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}

//            val news = mutableListOf<RedditNewsItem>()
//            for (i in 1..10) {
//                news.add(RedditNewsItem(
//                        "author$i",
//                        "Title $i",
//                        i, // number of comments
//                        1457207701L - i * 200, // time
//                        "http://lorempixel.com/200/200/technics/$i", // image url
//                        "url"
//                ))
//            }