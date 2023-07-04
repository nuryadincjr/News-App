package com.dna.newsapp.ui.screen.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    onRefresh: () -> Unit,
    onTapItem: (String) -> Unit,
    masterListLazyListState: LazyListState,
    modifier: Modifier = Modifier,
) {

}
