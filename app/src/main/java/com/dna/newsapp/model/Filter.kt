package com.dna.newsapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector

@Stable
class Filter(
    val name: String,
    val key: String,
    val icon: ImageVector? = null
)

val sortFilters = listOf(
    Filter(name = "Relevancy", key = "relevancy", icon = Icons.Filled.Android),
    Filter(name = "Popularity", key = "popularity", icon = Icons.Filled.Star),
    Filter(name = "Published At", key = "publishedAt", icon = Icons.Filled.SortByAlpha)
)

var sortDefault = sortFilters[0]