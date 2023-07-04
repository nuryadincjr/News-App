package com.dna.newsapp.data.remote

import com.dna.newsapp.BuildConfig
import com.dna.newsapp.BuildConfig.DEBUG
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

class ApiConfig {
    companion object {
        private const val baseUrl = BuildConfig.BASE_URL
        const val apiKey = BuildConfig.API_KEY

        fun getApiService(): ApiService {
            val level = if (DEBUG) Level.BODY else Level.NONE
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(level)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, SECONDS)
                .readTimeout(60, SECONDS)
                .writeTimeout(60, SECONDS)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}