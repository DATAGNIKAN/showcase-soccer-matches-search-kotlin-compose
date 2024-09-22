package com.example.showcase_soccer_matches_search_kotlin_compose.models

import java.time.LocalDateTime

data class Match(
    val date: LocalDateTime,
    val home: String,
    val away: String,
    val home_goals: Int,
    val away_goals: Int,
    val home_total_points: Double,
    val away_total_points: Double,
)