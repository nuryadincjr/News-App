package com.dna.newsapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dna.newsapp.R
import com.dna.newsapp.model.Articles
import com.dna.newsapp.model.Filter
import com.dna.newsapp.model.sortDefault

@Composable
fun ArticleList(
    articles: List<Articles?>?,
    lazyGridState: LazyGridState,
    onTapItem: (String) -> Unit,
    modifier: Modifier = Modifier,
    isSearch: Boolean = false,
    totalResults: Int?,
    sortFilters: List<Filter>,
    onUpdate: ((String, String) -> Unit)? = null,
) {
    var filterState by remember { mutableStateOf(false) }
    val sortState = remember { mutableStateOf(sortDefault) }
    val fromDateState = remember { mutableStateOf("") }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        if (isSearch) {
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                ResultFilterSort(resultCount = totalResults, onClick = { filterState = true })
            }
        }
        if (!articles.isNullOrEmpty()) {
            articles.forEachIndexed { index, item ->
                if (item != null) {

                    if (index % 5 == 0) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            NewsBigItem(article = item, onTapItem = onTapItem)
                        }
                    } else {
                        item(span = { GridItemSpan(1) }) {
                            NewsItem(
                                article = item,
                                onTapItem = onTapItem,
                            )
                        }
                    }
                }
            }
        } else {
            item(span = { GridItemSpan(maxLineSpan) }) {
                SearchHandler(
                    modifier = modifier,
                    errorMessage = stringResource(R.string.error_search),
                    imageVector = Icons.Rounded.SearchOff,
                )
            }
        }
    }

    if (filterState) {
        Dialog(onDismissRequest = { filterState = false }) {
            FilterBar(
                filters = sortFilters,
                onUpdate = { sort, from ->
                    if (onUpdate != null) {
                        filterState = false
                        sortState.value = sort
                        fromDateState.value = from

                        onUpdate(sortState.value.key, fromDateState.value)
                    }
                },
                sortState = sortState,
                fromDate = fromDateState
            )
        }
    }
}
