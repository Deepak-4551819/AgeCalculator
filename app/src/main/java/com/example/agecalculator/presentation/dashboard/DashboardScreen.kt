package com.example.agecalculator.presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agecalculator.domain.model.Occasion
import com.example.agecalculator.presentation.component.StylizedAgeText
import com.example.agecalculator.presentation.theme.spacing
import com.example.agecalculator.presentation.util.toFormattedDateString

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {

    val dummyOccasions = List(20) {
        Occasion(
            id = 1,
            title = "Birthday",
            dateMillis = 0L,
            emoji = "🎂"

        )
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DashboardTopBar(
            onAddIconClick = { }
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 400.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            items(dummyOccasions) { occasion ->
                OccasionCard(
                    modifier = Modifier.fillMaxWidth(),
                    occasion = occasion,
                    onCalendarIconClick = {},
                    onClick = {}
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopBar(
    modifier: Modifier = Modifier,
    onAddIconClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = "Dashboard")
        },
        actions = {
            IconButton(onClick = onAddIconClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Delete"
                )

            }
        }
    )
}

@Composable
private fun OccasionCard(
    modifier: Modifier = Modifier,
    occasion: Occasion,
    onCalendarIconClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.small,
                    top = MaterialTheme.spacing.medium
                )
        ) {
            Text(text = occasion.emoji, fontSize = 30.sp)
            Column {
                Text(text = occasion.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = occasion.dateMillis.toFormattedDateString(), color = Color.Gray)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onCalendarIconClick) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Calender"
                )

            }

        }
        StylizedAgeText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.small,
                    top = MaterialTheme.spacing.medium
                ),
            years = 25,
            months = 12,
            days = 28
        )
        FilledIconButton (
            onClick = onClick,
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .align(Alignment.End)
                .size(25.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(modifier = Modifier
                .padding(MaterialTheme.spacing.extraSmall),
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Show Details"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDashboardScreen() {
    DashboardScreen()
}
