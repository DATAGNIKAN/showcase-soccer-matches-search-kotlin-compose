package com.example.showcase_soccer_matches_search_kotlin_compose.dto

import com.example.showcase_soccer_matches_search_kotlin_compose.models.Filter

data class FilterDto(
    val perPage: Int = 20,
    val facets: List<Filter>? = null
)