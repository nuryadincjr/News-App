package com.dna.newsapp.ui.screen.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LukaKuRoute(
    viewModel: HomeViewModel,
    onTapItem: (String) -> Unit,
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val masterListLazyListState = rememberLazyListState()

    HomeScreen(
        homeUiState = homeUiState,
        onRefresh = { viewModel.refreshData() },
        onTapItem = onTapItem,
        masterListLazyListState = masterListLazyListState
    )
}