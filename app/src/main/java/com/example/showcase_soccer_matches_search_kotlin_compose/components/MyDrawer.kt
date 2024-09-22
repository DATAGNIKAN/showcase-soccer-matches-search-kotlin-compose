package com.example.showcase_soccer_matches_search_kotlin_compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.showcase_soccer_matches_search_kotlin_compose.dto.FilterDto
import com.example.showcase_soccer_matches_search_kotlin_compose.events.MatchesEvents
import com.example.showcase_soccer_matches_search_kotlin_compose.models.Filter
import com.example.showcase_soccer_matches_search_kotlin_compose.states.MatchFiltersState

@Composable
fun MyDrawer(
    filters: State<MatchFiltersState>,
    onFilter: (MatchesEvents.FilterMatches) -> Unit,
) {
    var filterBy by remember { mutableStateOf<List<Filter>>(emptyList()) }

    LaunchedEffect(filters.value) {
        filterBy = emptyList()
    }

    Surface(
        modifier = Modifier
            .background(color = Color.White)
            .padding(10.dp),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(fraction = .65f),
            verticalArrangement = Arrangement.Center,
        ) {
                filters.value.filters?.let {filters -> items(filters.size) {
                    MatchFilterItem(
                        modifier = Modifier.padding(top = 10.dp),
                        matchFilter = filters[it],
                        filterBy = filterBy,
                        handleCheck = { checked, filter ->
                            filterBy = if (checked) {
                                filterBy + filter
                            } else {
                                filterBy - filter
                            }
                            onFilter(MatchesEvents.FilterMatches(FilterDto(facets = filterBy.toList())))
                        }
                    )
                }
            }
        }
    }
}