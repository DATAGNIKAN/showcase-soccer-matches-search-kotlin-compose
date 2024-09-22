package com.example.showcase_soccer_matches_search_kotlin_compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.showcase_soccer_matches_search_kotlin_compose.components.MatchList
import com.example.showcase_soccer_matches_search_kotlin_compose.components.SearchBar
import com.example.showcase_soccer_matches_search_kotlin_compose.events.MatchesEvents
import com.example.showcase_soccer_matches_search_kotlin_compose.models.Match


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchesSearchPage(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    onSearch: (MatchesEvents.SearchMatches) -> Unit,
    onLoadMore: (MatchesEvents.LoadMoreMatches) -> Unit,
    refresh: (MatchesEvents.SearchMatches) -> Unit,
    matches: List<Match>,
    isLoading: Boolean
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp)) {

        val fontSize = 13.sp
        val fontWeight = FontWeight.Bold

        val brush = Brush.linearGradient(colors = listOf(
            Color(0xFF920292),
            Color(0xFFFF0000)))

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp)
        ){
            Text(text = "Powered by",
                fontSize = fontSize)
            Text(text = "Typesense",
                fontSize = fontSize,
                fontWeight = fontWeight,
                color = Color(0xffd90368))
            Text(text = " & ",
                fontSize = fontSize)
            Text(text = "Kotlin",
                fontSize = fontSize,
                fontWeight = fontWeight,
                style = TextStyle(
                    brush= brush
                )
            )
        }
        SearchBar(onSearch, openDrawer)
        MatchList(matches, onLoadMore, refresh, isLoading)
    }
}