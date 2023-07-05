package com.dna.newsapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dna.newsapp.data.local.entity.NewsEntity

@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}