package com.dna.newsapp.data.local.room

import androidx.room.*
import com.dna.newsapp.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY id ASC")
    fun getNews(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(entities: NewsEntity)

    @Query("DELETE FROM news WHERE id = :id")
    suspend fun deleteNews(id: String)
}