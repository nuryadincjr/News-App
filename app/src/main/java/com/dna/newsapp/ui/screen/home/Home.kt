package com.dna.newsapp.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dna.newsapp.R
import com.dna.newsapp.model.Filter
import com.dna.newsapp.ui.components.AppBar
import com.dna.newsapp.ui.components.ArticleList
import com.dna.newsapp.ui.components.ErrorLoad
import com.dna.newsapp.ui.components.LoadingContent
import com.dna.newsapp.ui.components.LoadingItem

@Composable
fun Home(
    viewModel: HomeViewModel,
    onTapItem: (String) -> Unit,
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val lazyGridState = rememberLazyGridState()

    Home(
        homeUiState = homeUiState,
        onRefresh = { viewModel.refreshData() },
        onTapItem = onTapItem,
        lazyGridState = lazyGridState,
        sortFilters = viewModel.filterList,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Home(
    homeUiState: HomeUiState,
    onRefresh: () -> Unit,
    onTapItem: (String) -> Unit,
    lazyGridState: LazyGridState,
    sortFilters: List<Filter>,
    modifier: Modifier = Modifier,
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    Scaffold(
        topBar = {
            AppBar(onActions = onTapItem)
        },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier
            .padding(innerPadding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)

        LoadingContent(
            empty = homeUiState is HomeUiState.NoData,
            emptyContent = { LoadingItem(contentModifier, lazyGridState) },
            loading = homeUiState.isLoading,
            onRefresh = onRefresh,
            content = {
                when (homeUiState) {
                    is HomeUiState.HasNews -> {
                        ArticleList(
                            totalResults = homeUiState.newsResponse.totalResults,
                            articles = homeUiState.newsResponse.articles,
                            lazyGridState = lazyGridState,
                            onTapItem = onTapItem,
                            sortFilters = sortFilters,
                            modifier = contentModifier
                        )
                    }

                    is HomeUiState.NoData -> {
                        if (homeUiState.errorMessages.isEmpty()) {
                            TextButton(
                                onClick = onRefresh,
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

        if (homeUiState.errorMessages.isNotEmpty()) {
            ErrorLoad(contentModifier, homeUiState.errorMessages, onRefresh)
        }
    }
}