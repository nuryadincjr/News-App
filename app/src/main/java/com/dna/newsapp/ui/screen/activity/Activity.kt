package com.dna.newsapp.ui.screen.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dna.newsapp.R
import com.dna.newsapp.data.local.entity.NewsEntity
import com.dna.newsapp.ui.components.AppBar
import com.dna.newsapp.ui.components.ErrorLoad
import com.dna.newsapp.ui.components.LoadingContent
import com.dna.newsapp.ui.components.NewsItemRow

@Composable
fun Activity(
    viewModel: ActivityViewModel,
    onTapItem: (String) -> Unit,
    onTapContent: (NewsEntity) -> Unit,
) {
    val activityUiState by viewModel.activityUiState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    Activity(
        activityUiState = activityUiState,
        onRefresh = { viewModel.refreshData() },
        onTapItem = onTapItem,
        onTapContent = { value, data ->
            when (value) {
                "Delete" -> viewModel.deleteNews(data.id)
                else -> onTapContent(data)
            }
        },
        lazyListState = lazyListState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Activity(
    activityUiState: ActivityUiState,
    onRefresh: () -> Unit,
    onTapItem: (String) -> Unit,
    onTapContent: (String, NewsEntity) -> Unit,
    lazyListState: LazyListState,
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
            empty = activityUiState is ActivityUiState.NoData,
            emptyContent = { },
            loading = activityUiState.isLoading,
            onRefresh = onRefresh,
            content = {
                when (activityUiState) {
                    is ActivityUiState.HasNews -> {
                        ArticleList(
                            articles = activityUiState.newsEntity,
                            lazyListState = lazyListState,
                            onTapContent = onTapContent,
                            modifier = contentModifier
                        )
                    }

                    is ActivityUiState.NoData -> {
                        if (activityUiState.errorMessages.isEmpty()) {
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

        if (activityUiState.errorMessages.isNotEmpty()) {
            ErrorLoad(contentModifier, activityUiState.errorMessages, onRefresh)
        }
    }
}


@Composable
fun ArticleList(
    articles: List<NewsEntity>?,
    lazyListState: LazyListState,
    onTapContent: (String, NewsEntity) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        if (!articles.isNullOrEmpty()) {
            items(articles) { articles ->
                NewsItemRow(article = articles, onTapItem = { onTapContent(it, articles) })
            }
        }
    }
}
