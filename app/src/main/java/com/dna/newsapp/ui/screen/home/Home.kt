package com.dna.newsapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dna.newsapp.R
import com.dna.newsapp.model.Articles
import com.dna.newsapp.ui.components.AppBar
import com.dna.newsapp.ui.components.NewsBigItem
import com.dna.newsapp.ui.components.NewsItem
import com.dna.newsapp.ui.components.ShimmerNewsBigItem
import com.dna.newsapp.ui.components.ShimmerNewsItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

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
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Home(
    homeUiState: HomeUiState,
    onRefresh: () -> Unit,
    onTapItem: (String) -> Unit,
    lazyGridState: LazyGridState,
    modifier: Modifier = Modifier,
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    Scaffold(
        topBar = {
            AppBar(onActions = {})
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
                            articles = homeUiState.newsResponse.articles,
                            lazyGridState = lazyGridState,
                            onTapItem = onTapItem,
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
    }

    if (homeUiState.errorMessages.isNotEmpty()) {
        ErrorLoad(modifier, homeUiState, onRefresh)
    }
}

@Composable
fun ArticleList(
    articles: List<Articles?>?,
    lazyGridState: LazyGridState,
    onTapItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
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
        }
    }
}


@Composable
private fun ErrorLoad(
    modifier: Modifier,
    homeUiState: HomeUiState,
    onRefresh: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Outlined.Error,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Text(text = homeUiState.errorMessages, textAlign = TextAlign.Center)
            Button(
                onClick = onRefresh,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.retry),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun LoadingItem(modifier: Modifier = Modifier, lazyGridState: LazyGridState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        val shimmers = 1..10
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
        ) {

            shimmers.forEachIndexed { index, _ ->
                if (index % 5 == 0) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ShimmerNewsBigItem()
                    }
                } else {
                    item(span = { GridItemSpan(1) }) {
                        ShimmerNewsItem()
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}
