package com.example.averyw.redditapp.features

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.averyw.redditapp.R
import com.example.averyw.redditapp.commons.RedditNewsItem
import com.example.averyw.redditapp.features.adapter.NewsAdapter
import com.example.averyw.redditapp.commons.extensions.inflate
import kotlinx.android.synthetic.main.news_fragment.*
import rx.schedulers.Schedulers

/**
 * Created by averyw on 4/12/2019.
 */

class NewsFragment : Fragment() {
    private val newsManager by lazy { NewsManager() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.news_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        news_list.setHasFixedSize(true)
        news_list.layoutManager = LinearLayoutManager(context)
        initAdapter()

        if (savedInstanceState == null) {
            requestNews()
        }
    }

    private fun requestNews() {
        val subscription = newsManager.getNews()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            retrievedNews ->
                            (news_list.adapter as NewsAdapter).addNews(retrievedNews)
                        },
                        { e ->
                            Snackbar.make(news_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                        }
                )
    }

    private fun initAdapter() {
        if (news_list.adapter == null) {
            news_list.adapter = NewsAdapter()
        }
    }
}