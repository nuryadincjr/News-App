package com.dna.newsapp.data.repository.news

import com.dna.newsapp.data.local.entity.NewsEntity
import com.dna.newsapp.model.Filter
import com.dna.newsapp.model.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(
        q: String,
        from: String? = null,
        sortBy: String? = null,
    ): Flow<Result<NewsResponse>>

    suspend fun getNews(
        country: String,
    ): Flow<Result<NewsResponse>>

    fun getSortDefault(): Filter

    fun getSortFilter(): List<Filter>

    fun getNews():Flow<List<NewsEntity>?>

    suspend fun insertNews(entities: NewsEntity)

    suspend fun deleteNews(id: String)
}