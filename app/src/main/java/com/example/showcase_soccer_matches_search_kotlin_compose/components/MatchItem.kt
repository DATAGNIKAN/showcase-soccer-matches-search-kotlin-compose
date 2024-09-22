package com.example.showcase_soccer_matches_search_kotlin_compose.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.showcase_soccer_matches_search_kotlin_compose.models.Match
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchItem(modifier: Modifier = Modifier, match: Match) {
    Column(
        modifier= modifier.fillMaxWidth().border(width = 1.dp, color = Color.Black.copy(.6f), shape = CircleShape.copy(all = CornerSize(5.dp))).padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = match.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TeamNameIcon(match.home)
                Text(modifier = Modifier.padding(top = 10.dp), text = match.home)
            }
            Text(
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                text = "${match.home_goals}:${match.away_goals}"
            )
            Column (
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TeamNameIcon(match.away)
                Text(modifier = Modifier.padding(top = 10.dp), text = match.away)
            }
        }
    }
}