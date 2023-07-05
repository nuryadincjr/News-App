package com.dna.newsapp.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalActivity
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dna.newsapp.R
import com.dna.newsapp.data.local.entity.NewsEntity
import com.dna.newsapp.ui.screen.activity.Activity
import com.dna.newsapp.ui.screen.activity.ActivityViewModel
import com.dna.newsapp.ui.screen.home.Home
import com.dna.newsapp.ui.screen.home.HomeViewModel
import com.dna.newsapp.ui.screen.search.Search
import com.dna.newsapp.ui.screen.search.SearchViewModel

fun NavGraphBuilder.mainNavGraph(
    onTapItem: (String) -> Unit,
    onTapContent: (NewsEntity) -> Unit,
) {
    composable(route = MainDestinations.HOME_ROUTE) {
        val homeViewModel: HomeViewModel = hiltViewModel()

        Home(
            viewModel = homeViewModel,
            onTapItem = { value -> onTapItem(value) },
            onTapContent = {
                homeViewModel.insertNews(it)
                onTapContent(it)
            }
        )
    }

    composable(route = MainDestinations.SEARCH_ROUTE) {
        val searchViewModel: SearchViewModel = hiltViewModel()

        Search(
            viewModel = searchViewModel
        ) {
            searchViewModel.insertNews(it)
            onTapContent(it)
        }
    }
    composable(route = MainDestinations.HISTORY_ROUTE) {
        val activityViewModel: ActivityViewModel = hiltViewModel()

        Activity(
            viewModel = activityViewModel,
            onTapItem = { value -> onTapItem(value) },
            onTapContent = onTapContent
        )
    }
}

enum class MainTabs(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.home, Icons.Outlined.Home, MainDestinations.HOME_ROUTE),
    VIDEO(R.string.search, Icons.Outlined.Search, MainDestinations.SEARCH_ROUTE),
    ACTIVITY(R.string.history, Icons.Outlined.LocalActivity, MainDestinations.HISTORY_ROUTE),
}

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
    const val HISTORY_ROUTE = "history"
}
