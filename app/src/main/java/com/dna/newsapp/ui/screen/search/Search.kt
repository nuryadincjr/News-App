package com.dna.newsapp.ui.screen.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dna.newsapp.R
import com.dna.newsapp.model.Filter
import com.dna.newsapp.ui.components.ArticleList
import com.dna.newsapp.ui.components.ErrorLoad
import com.dna.newsapp.ui.components.LoadingContent
import com.dna.newsapp.ui.components.LoadingItem
import com.dna.newsapp.ui.components.SearchBar
import com.dna.newsapp.ui.components.SearchHandler

@Composable
fun Search(
    viewModel: SearchViewModel,
    onTapItem: (String) -> Unit,
) {
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()
    val lazyGridState = rememberLazyGridState()

    Search(
        searchUiState = searchUiState,
        onRefresh = { query, sort, form ->
            if (query.isNotEmpty()) viewModel.refreshData(
                query,
                sort,
                form
            )
        },
        onTapItem = onTapItem,
        sortFilters = viewModel.filterList,
        lazyGridState = lazyGridState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Search(
    searchUiState: SearchUiState,
    onRefresh: (String, String, String) -> Unit,
    onTapItem: (String) -> Unit,
    lazyGridState: LazyGridState,
    sortFilters: List<Filter>,
    modifier: Modifier = Modifier,
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    val query = remember { mutableStateOf("") }
    var sortState by remember { mutableStateOf("") }
    var fromState by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SearchBar(
                searchInput = query,
                onSearchInputChanged = { query.value = it },
                modifier = Modifier.padding(16.dp)
            ) { onRefresh(it, sortState, fromState) }
        },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier
            .padding(innerPadding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)

        LoadingContent(
            empty = searchUiState is SearchUiState.NoData,
            emptyContent = {
                if (!searchUiState.isLoading) {
                    SearchHandler(
                        modifier = modifier,
                        errorMessage = stringResource(R.string.try_search),
                        imageVector = Icons.Rounded.Explore,
                    )
                } else {
                    LoadingItem(contentModifier, lazyGridState)
                }
            },
            loading = searchUiState.isLoading,
            onRefresh = {
                onRefresh(query.value, sortState, fromState)
            },
            content = {
                when (searchUiState) {
                    is SearchUiState.HasNews -> {
                        ArticleList(
                            articles = searchUiState.newsResponse.articles,
                            lazyGridState = lazyGridState,
                            onTapItem = onTapItem,
                            isSearch = true,
                            sortFilters = sortFilters,
                            onUpdate = { sort, from ->
                                sortState = sort
                                fromState = from
                                onRefresh(query.value, sort, from)
                            },
                            totalResults = searchUiState.newsResponse.totalResults,
                        )
                    }

                    is SearchUiState.NoData -> {
                        if (searchUiState.errorMessages.isEmpty()) {
                            TextButton(
                                onClick = { onRefresh(query.value, sortState, fromState) },
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = stringResource(R.string.load_article),
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            Box(modifier = contentModifier.fillMaxSize())
                        }
                    }
                }
            }
        )

        if (searchUiState.errorMessages.isNotEmpty()) {
            ErrorLoad(contentModifier, searchUiState.errorMessages) {
                onRefresh(
                    query.value,
                    sortState,
                    fromState
                )
            }
        }
    }
}