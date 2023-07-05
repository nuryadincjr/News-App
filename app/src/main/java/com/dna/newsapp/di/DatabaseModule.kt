package com.dna.newsapp.di

import android.content.Context
import androidx.room.Room
import com.dna.newsapp.data.local.room.NewsDao
import com.dna.newsapp.data.local.room.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {

        return Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "NewsApp.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(database: NewsDatabase): NewsDao =
        database.newsDao()
}