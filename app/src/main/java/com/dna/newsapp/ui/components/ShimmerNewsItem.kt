package com.dna.newsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dna.newsapp.ui.theme.NewsAppTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerNewsItem(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(280.dp, 240.dp)
            .shimmer()
    ) {

        Column {
            Box(
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(Color.Black.copy(alpha = 0.6f)),
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight(1f),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier
                        .height(18.dp)
                        .fillMaxWidth()
                        .clip(shape = MaterialTheme.shapes.medium)
                        .background(Color.Black.copy(alpha = 0.6f)),
                )
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .width(100.dp)
                        .fillMaxWidth()
                        .clip(shape = MaterialTheme.shapes.medium)
                        .background(Color.Black.copy(alpha = 0.6f)),
                )
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(150.dp)
                        .fillMaxWidth()
                        .clip(shape = MaterialTheme.shapes.medium)
                        .background(Color.Black.copy(alpha = 0.6f)),
                )
            }
        }
    }
}

@Preview
@Composable
fun ShimmerNewsItemPreview() {
    NewsAppTheme {
        Surface {
            ShimmerNewsItem()
        }
    }
}

