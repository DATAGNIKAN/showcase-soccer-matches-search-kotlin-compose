package com.example.showcase_soccer_matches_search_kotlin_compose.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.showcase_soccer_matches_search_kotlin_compose.dto.LoadMoreDto
import com.example.showcase_soccer_matches_search_kotlin_compose.dto.SearchDto
import com.example.showcase_soccer_matches_search_kotlin_compose.events.MatchesEvents
import com.example.showcase_soccer_matches_search_kotlin_compose.models.Match


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchList(
    matches: List<Match>,
    onLoadMore: (MatchesEvents.LoadMoreMatches) -> Unit,
    refresh: (MatchesEvents.SearchMatches) -> Unit,
    isLoading: Boolean
) {
    val listState = rememberLazyListState()

    InfiniteListHandler(listState = listState) {
        onLoadMore(MatchesEvents.LoadMoreMatches(LoadMoreDto()))
    }

    if(!isLoading && matches.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(fontWeight = FontWeight.Bold, text = "NO DATA")
            Button(
                modifier = Modifier.padding(top = 10.dp),
                onClick = {refresh(MatchesEvents.SearchMatches(SearchDto("*")))}
            ) {
                Text(text = "Refresh")
            }
        }
    }else {
        LazyColumn (
            state = listState
        ) {
            matches.let {
                matches -> items(matches.size) {
                    MatchItem(
                        modifier = Modifier.padding(vertical = 5.dp),
                        match = matches[it]
                    )
                }
            }

            if (isLoading) {
                item {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 3,
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItemCount = listState.layoutInfo.totalItemsCount

            lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItemCount - buffer
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            loadMore()
        }
    }
}