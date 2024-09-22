package com.example.showcase_soccer_matches_search_kotlin_compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun TeamNameIcon(teamName: String) {
    val red = Random.nextInt(156, 256)
    val green = Random.nextInt(156, 256)
    val blue = Random.nextInt(156, 256)
    val color = Color(red, green, blue)

    Box (
        modifier = Modifier
            .width(55.dp)
            .height(55.dp)
            .clip(RoundedCornerShape(55.dp))
            .background(color = color),
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                text = teamName.slice(0..2).uppercase()
            )
        }
    }
}