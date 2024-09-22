package com.example.showcase_soccer_matches_search_kotlin_compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.showcase_soccer_matches_search_kotlin_compose.R
import com.example.showcase_soccer_matches_search_kotlin_compose.events.MatchesEvents
import com.example.showcase_soccer_matches_search_kotlin_compose.dto.SearchDto

@Composable
fun SearchBar(
    onSearch: (MatchesEvents.SearchMatches) -> Unit,
    openDrawer: () -> Unit
    ) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        value = text,
        placeholder = { Text(text = "Search")},
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Drawer Icon",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable(onClick = {
                        openDrawer()
                    })
            )
        },
        onValueChange = { newText: String ->
            text = newText
            onSearch(MatchesEvents.SearchMatches(SearchDto(query = text)))
        }
    )
}