package com.example.averyw.redditapp.features

import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.averyw.redditapp.R
import com.example.averyw.redditapp.commons.InfiniteScrollListener
import com.example.averyw.redditapp.commons.RedditNews
import com.example.averyw.redditapp.commons.RxBaseFragment
import com.example.averyw.redditapp.commons.extensions.androidLazy
import com.example.averyw.redditapp.features.adapter.NewsAdapter
import com.example.averyw.redditapp.commons.extensions.inflate
import com.example.averyw.redditapp.features.adapter.NewsDelegateAdapter
import kotlinx.android.synthetic.main.news_fragment.*
import rx.schedulers.Schedulers

/**
 * Created by averyw on 4/12/2019.
 */

class NewsFragment : RxBaseFragment(), NewsDelegateAdapter.onViewSelectedListener {

    override fun onItemSelected(url: String?) {
        if (url.isNullOrEmpty()) {
            Snackbar.make(news_list, "No URL assigned to this news", Snackbar.LENGTH_LONG).show()
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    companion object {
        private val KEY_REDDIT_NEWS = "redditNews"
    }

    private  var redditNews: RedditNews? = null
    private val newsManager by lazy { NewsManager() }
    private  val newsAdapter by androidLazy { NewsAdapter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.news_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        news_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))
        }

        initAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
            (news_list.adapter as NewsAdapter).clearAndAddNews(redditNews!!.news)
        } else {
            requestNews()
        }
    }

    private fun requestNews() {
        val subscription = newsManager.getNews(redditNews?.after ?: "")
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {retrievedNews ->
                            redditNews = retrievedNews
                            (news_list.adapter as NewsAdapter).addNews(retrievedNews.news)
                        },
                        { e ->
                            Snackbar.make(news_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                        }
                )
        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if (news_list.adapter == null) {
            news_list.adapter = newsAdapter
        }
    }
}