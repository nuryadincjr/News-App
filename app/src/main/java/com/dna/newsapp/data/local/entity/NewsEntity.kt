package com.dna.newsapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    @field:ColumnInfo("id")
    val id: String,

    @field:ColumnInfo("url_to_image")
    val urlToImage: String? = null,

    @field:ColumnInfo("title")
    val title: String,

    @field:ColumnInfo("url")
    val url: String,
)
