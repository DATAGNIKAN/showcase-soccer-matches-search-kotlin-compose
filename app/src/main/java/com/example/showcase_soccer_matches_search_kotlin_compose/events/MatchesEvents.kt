package com.example.showcase_soccer_matches_search_kotlin_compose.events

import com.example.showcase_soccer_matches_search_kotlin_compose.dto.FilterDto
import com.example.showcase_soccer_matches_search_kotlin_compose.dto.LoadMoreDto
import com.example.showcase_soccer_matches_search_kotlin_compose.dto.SearchDto

sealed interface MatchesEvents {
    data class SearchMatches(val searchDto: SearchDto): MatchesEvents
    data class FilterMatches(val filterDto: FilterDto): MatchesEvents
    data class LoadMoreMatches(val loadMoreDto: LoadMoreDto): MatchesEvents
}