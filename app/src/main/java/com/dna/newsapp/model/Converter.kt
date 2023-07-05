package com.dna.newsapp.model

import com.dna.newsapp.data.local.entity.NewsEntity

fun Articles.toNewsEntity(): NewsEntity {
    return NewsEntity(
        id = this.title,
        urlToImage = this.urlToImage,
        title = this.title,
        url = this.url
    )
}
