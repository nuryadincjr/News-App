package com.dna.newsapp.data.repository.news.impl

import com.dna.newsapp.model.Articles
import com.dna.newsapp.model.NewsResponse
import com.dna.newsapp.model.Source

val newsResponse = NewsResponse(
    totalResults = 10,
    articles = listOf(
        Articles(
            publishedAt = "2023-04-25T20:34:00Z",
            author = "BBC Sport",
            urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/248E/production/_130285390_gettyimages-1513862155.jpg",
            description = "Eight-time Wimbledon champion Roger Federer takes the acclaim of an adoring Centre Court crowd in a special ceremony on day two to mark his achievements at the tournament.",
            source = Source(
                name = "BBC News",
                id = "bbc-news"
            ),
            title = "Eight-time Wimbledon champion Federer honoured",
            url = "http://www.bbc.co.uk/sport/tennis/66097818",
            content = "<table><tr><th>Wimbledon 2023 on the BBC</th></tr>\r\n<tr><td>Venue: All England Club Dates: 3-16 July</td></tr><tr><td>Coverage: Live across BBC TV, radio and online with extensive coverage on BBC iPl… [+2216 chars]"
        )
    ),
    status = "ok"
)

// Mengakses data dalam objek NewsResponse
val totalResults = newsResponse.totalResults
val articles = newsResponse.articles
val status = newsResponse.status

// Mengakses data dalam objek Articles
val article = articles?.get(0)
val publishedAt = article?.publishedAt
val author = article?.author
val urlToImage = article?.urlToImage
val description = article?.description
val source = article?.source
val title = article?.title
val url = article?.url
val content = article?.content
