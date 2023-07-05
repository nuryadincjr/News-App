package com.dna.newsapp.data.repository.news.impl

import com.dna.newsapp.data.local.entity.NewsEntity
import com.dna.newsapp.data.local.room.NewsDao
import com.dna.newsapp.data.remote.ApiService
import com.dna.newsapp.data.repository.news.NewsRepository
import com.dna.newsapp.model.Filter
import com.dna.newsapp.model.NewsResponse
import com.dna.newsapp.model.sortDefault
import com.dna.newsapp.model.sortFilters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiService, private val dao: NewsDao
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

    override suspend fun getNews(): Flow<Result<List<NewsEntity>>> = flow {
        try {
            val response = dao.getNews()
            emit(Result.success(response))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }


    override fun getSortDefault(): Filter = sortDefault

    override fun getSortFilter(): List<Filter> = sortFilters
    override suspend fun insertNews(entities: NewsEntity): Long {
        return withContext(Dispatchers.IO) {
            dao.insertNews(entities)
        }
    }

    override suspend fun deleteNews(id: String): Int {
        return withContext(Dispatchers.IO) {
            dao.deleteNews(id)
        }
    }
}
