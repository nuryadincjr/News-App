package com.dna.newsapp.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.dna.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp() {
    val appState = rememberAppState(LocalContext.current)

    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                AppBottomBar(
                    tabs = appState.bottomBarTabs,
                    currentRoute = appState.currentRoute!!,
                    navigateToRoute = appState::navigateToRoute
                )
            }
        },
    ) { innerPadding ->
        Row {
            NavHost(
                navController = appState.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                modifier = Modifier.padding(innerPadding),
            ) {
                mainNavGraph(
                    onTapItem = appState::onNavigateToSearch,
                    onTapContent = { appState.onNavigateToDetail(it.url) },
                )
            }
        }
    }
}

@Composable
private fun AppBottomBar(
    tabs: Array<MainTabs>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
) {

    val currentSection = tabs.first { it.route == currentRoute }

    NavigationBar(
        Modifier.windowInsetsBottomHeight(
            WindowInsets.navigationBars.add(WindowInsets(bottom = 56.dp))
        )
    ) {

        tabs.forEach { tab ->
            val selected = tab == currentSection

            NavigationBarItem(icon = {
                Icon(
                    imageVector = tab.icon, contentDescription = stringResource(id = tab.title)
                )
            }, selected = selected, onClick = {
                navigateToRoute(tab.route)
            }, modifier = Modifier.navigationBarsPadding()
            )
        }
    }
}

@Preview
@Composable
fun NewsAppPreview() {
    NewsAppTheme {
        NewsApp()
    }
}
