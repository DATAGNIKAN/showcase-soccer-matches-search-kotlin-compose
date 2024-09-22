package com.example.showcase_soccer_matches_search_kotlin_compose.states

import com.example.showcase_soccer_matches_search_kotlin_compose.models.Match

data class MatchesState(
    val matches: List<Match>? = null,
)