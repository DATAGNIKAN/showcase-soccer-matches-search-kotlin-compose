package com.example.showcase_soccer_matches_search_kotlin_compose.dto

data class SearchDto(
    val query: String,
    val perPage: Int = 20
)