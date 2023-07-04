package com.dna.newsapp.data.repository.news.impl

import com.dna.newsapp.data.remote.ApiService
import com.dna.newsapp.data.repository.news.NewsRepository
import com.dna.newsapp.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : NewsRepository {
    override suspend fun getNews(
        q: String,
        from: String?,
        sortBy: String?,
    ): Flow<Result<NewsResponse>> = flow {
        try {
            val response = apiService.getNews(q, from, sortBy)
            emit(Result.success(response))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }

    override suspend fun getNews(country: String): Flow<Result<NewsResponse>> = flow {
        try {
            val response = apiService.getNews(country)
            emit(Result.success(response))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }
}
