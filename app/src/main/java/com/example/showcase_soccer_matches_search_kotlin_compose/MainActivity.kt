package com.example.showcase_soccer_matches_search_kotlin_compose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.showcase_soccer_matches_search_kotlin_compose.components.MyDrawer
import com.example.showcase_soccer_matches_search_kotlin_compose.ui.theme.ShowcaseSoccerMatchesSearchKotlinComposeTheme
import com.example.showcase_soccer_matches_search_kotlin_compose.utils.setupTypesenseClient
import com.example.showcase_soccer_matches_search_kotlin_compose.viewmodels.MatchesViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShowcaseSoccerMatchesSearchKotlinComposeTheme {
                MainScaffoldWithDrawer()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffoldWithDrawer() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val client = setupTypesenseClient()

    val matchesViewModel = MatchesViewModel(client)
    val matches = matchesViewModel.matches.collectAsState()
    val isLoading = matchesViewModel.isLoading.collectAsState()
    val filters = matchesViewModel.filters.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MyDrawer(
                filters=filters,
                onFilter = matchesViewModel::onEvent,
            )
        },
    ) {
        Scaffold {
            MatchesSearchPage(
                modifier = Modifier.padding(it),
                openDrawer = {
                    scope.launch { drawerState.open() }
                },
                onSearch = matchesViewModel::onEvent,
                onLoadMore = matchesViewModel::onEvent,
                refresh = matchesViewModel::onEvent,
                matches = matches.value.matches ?: listOf(),
                isLoading = isLoading.value
            )
        }
    }
}