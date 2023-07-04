package com.dna.newsapp.data.remote

import com.dna.newsapp.model.NewsResponse
import retrofit2.http.*

interface ApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") fromDate: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("apiKey") apiKey: String = ApiConfig.apiKey,
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getNews(
        @Path("country") label: String,
        @Query("apiKey") type: String = ApiConfig.apiKey,
    ): NewsResponse
}