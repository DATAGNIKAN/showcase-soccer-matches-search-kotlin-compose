package com.example.showcase_soccer_matches_search_kotlin_compose.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.showcase_soccer_matches_search_kotlin_compose.models.Filter
import com.example.showcase_soccer_matches_search_kotlin_compose.models.MatchFilter

@Composable
fun MatchFilterItem(
    modifier: Modifier = Modifier,
    filterBy: List<Filter>,
    matchFilter: MatchFilter,
    handleCheck: (Boolean, Filter) -> Unit
) {
    Column (modifier = modifier.padding(horizontal = 5.dp)) {
        Text(text = matchFilter.fieldName.replaceFirstChar { it.uppercase() }, fontWeight = FontWeight.Bold)
        matchFilter.filters.map {
            Row (
                modifier = Modifier.fillMaxWidth().padding(start = 5.dp, top = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(text = it.filterName)
                    Text(text = it.count.toString(), fontSize = 11.sp, modifier = Modifier.padding(start = 2.dp))
                }
                Checkbox(checked = filterBy.find { one -> one.fieldName == it.fieldName && one.filterName == it.filterName} != null, onCheckedChange = {checked ->
                    handleCheck(checked, it)
                })
            }
        }
    }
}
