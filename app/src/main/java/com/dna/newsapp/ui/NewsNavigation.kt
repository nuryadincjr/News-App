package com.dna.newsapp.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalActivity
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dna.newsapp.R
import com.dna.newsapp.ui.screen.home.HomeViewModel
import com.dna.newsapp.ui.screen.home.Home
import com.dna.newsapp.ui.screen.search.Search
import com.dna.newsapp.ui.screen.search.SearchViewModel

fun NavGraphBuilder.mainNavGraph(
    onTapItem: (String, NavBackStackEntry) -> Unit,
) {
    composable(route = MainDestinations.HOME_ROUTE) { from ->
        val homeViewModel: HomeViewModel = hiltViewModel()

        Home(
            viewModel = homeViewModel,
        ) { value -> onTapItem(value, from) }
    }

    composable(route = MainDestinations.SEARCH_ROUTE) { from ->
        val searchViewModel: SearchViewModel = hiltViewModel()

        Search(
            viewModel = searchViewModel,
            onTapItem = { value -> onTapItem(value, from) },
        )
    }
    composable(route = MainDestinations.HISTORY_ROUTE) { from ->
//        val homeViewModel: HomeViewModel = hiltViewModel()

//        Activity(
//            viewModel = homeViewModel,
//            onTapItem = { value -> onTapItem(value, from) },
//        )
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
