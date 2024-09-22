package com.example.showcase_soccer_matches_search_kotlin_compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showcase_soccer_matches_search_kotlin_compose.events.MatchesEvents
import com.example.showcase_soccer_matches_search_kotlin_compose.models.Filter
import com.example.showcase_soccer_matches_search_kotlin_compose.models.Match
import com.example.showcase_soccer_matches_search_kotlin_compose.models.MatchFilter
import com.example.showcase_soccer_matches_search_kotlin_compose.dto.SearchDto
import com.example.showcase_soccer_matches_search_kotlin_compose.states.MatchFiltersState
import com.example.showcase_soccer_matches_search_kotlin_compose.states.MatchesState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.typesense.api.Client
import org.typesense.model.SearchParameters
import org.typesense.model.SearchResult
import org.typesense.model.SearchResultHit
import java.time.LocalDateTime
import kotlin.math.roundToInt

class MatchesViewModel(private val client: Client) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _matches = MutableStateFlow(MatchesState())
    val matches: StateFlow<MatchesState> = _matches.asStateFlow()

    private val _query = MutableStateFlow("")

    private val _filters = MutableStateFlow(MatchFiltersState())
    val filters: StateFlow<MatchFiltersState> = _filters.asStateFlow()

    private val _currentFilters = MutableStateFlow<String?>(null)

    private val _total = MutableStateFlow(0)

    private val _page = MutableStateFlow(0)

    init {
        searchMatches(MatchesEvents.SearchMatches(SearchDto(query = "*")))
    }

    fun onEvent(event: MatchesEvents) {
        when(event) {
            is MatchesEvents.SearchMatches -> searchMatches(event)
            is MatchesEvents.FilterMatches -> filterMatches(event)
            is MatchesEvents.LoadMoreMatches -> loadMoreMatches(event)
        }
    }

    private fun handleSearchResponse(response: SearchResult) {
        val matches = response.hits.map { hit ->
            hit.toMatch()
        }
        _matches.update { it.copy(matches = matches) }
        _page.update { response.page }
        _total.update { response.found }
    }

    private fun SearchResult.toFilters(): List<MatchFilter> {
        return facetCounts.map { facetCount ->
            MatchFilter(
                fieldName = facetCount.fieldName,
                filters = facetCount.counts.map { count ->
                    Filter(
                        fieldName = facetCount.fieldName,
                        filterName = count.value,
                        count = count.count
                    )
                }
            )
        }
    }

    private fun SearchResultHit.toMatch(): Match {
        val document = this.document
        return Match(
            date = LocalDateTime.parse(document["date"].toString()),
            home = document["home"].toString(),
            away = document["away"].toString(),
            home_goals = document["home_goals"].toString().toInt(),
            away_goals = document["away_goals"].toString().toInt(),
            home_total_points = document["home_total_points"].toString().toDouble(),
            away_total_points = document["away_total_points"].toString().toDouble()
        )
    }

    private fun buildFiltersMap(event: MatchesEvents.FilterMatches): MutableMap<String, List<String>> {
        val filters: MutableMap<String, List<String>> = mutableMapOf()
        event.filterDto.facets?.forEach {
            filters[it.fieldName] = filters[it.fieldName]?.plus(it.filterName) ?: listOf(it.filterName)
        }
        return filters
    }

    private fun buildSearchParameters(query: String, perPage: Int, filterBy: String? = null, page: Int = 1): SearchParameters {
        return SearchParameters().apply {
            q = query
            queryBy = "home,away"
            facetBy = "home,away"
            this.perPage = perPage
            this.page = page
            filterBy?.let { this.filterBy = it }
        }
    }

    private fun launchSearch(searchBlock: suspend () -> Unit) {
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                withContext(Dispatchers.IO) {
                    delay(1000)
                    searchBlock()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _matches.update { it.copy(matches = listOf()) }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun searchMatches(event: MatchesEvents.SearchMatches) = launchSearch {
        _query.update { event.searchDto.query }
        _currentFilters.update { "" }
        val searchParameters = buildSearchParameters(event.searchDto.query, event.searchDto.perPage)
        val response = client.collections("soccer_matches").documents().search(searchParameters)
        handleSearchResponse(response)
        _filters.update { it.copy(filters = response.toFilters()) }
    }

    private fun filterMatches(event: MatchesEvents.FilterMatches) = launchSearch {
        val filtersMap = buildFiltersMap(event)
        _currentFilters.update { filtersMap.map { "${it.key}:${it.value}" }.joinToString(" && ") }
        val searchParameters = buildSearchParameters(_query.value, event.filterDto.perPage, _currentFilters.value)
        val response = client.collections("soccer_matches").documents().search(searchParameters)
        handleSearchResponse(response)
    }

    private fun loadMoreMatches(event: MatchesEvents.LoadMoreMatches) = launchSearch {
        val totalPage = (_total.value / event.loadMoreDto.perPage.toDouble()).roundToInt()
        if (_page.value < totalPage) {
            val searchParameters = buildSearchParameters(_query.value, event.loadMoreDto.perPage, _currentFilters.value, _page.value + 1)
            val response = client.collections("soccer_matches").documents().search(searchParameters)
            val newMatches = response.hits.map { it.toMatch() }
            _matches.update { it.copy(matches = it.matches?.plus(newMatches)) }
            _page.update { response.page }
        }
    }
}