package com.example.showcase_soccer_matches_search_kotlin_compose.states

import com.example.showcase_soccer_matches_search_kotlin_compose.models.MatchFilter


data class MatchFiltersState(
    val filters: List<MatchFilter>? = null,
)