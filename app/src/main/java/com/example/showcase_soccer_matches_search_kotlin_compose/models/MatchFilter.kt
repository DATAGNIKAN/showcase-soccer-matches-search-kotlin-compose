package com.example.showcase_soccer_matches_search_kotlin_compose.models

data class MatchFilter(
    val fieldName: String,
    val filters: List<Filter>
)