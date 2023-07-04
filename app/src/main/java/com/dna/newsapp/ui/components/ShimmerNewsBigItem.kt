package com.dna.newsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun ShimmerNewsBigItem(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.shimmer(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(Color.Black.copy(alpha = 0.6f)),
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .height(100.dp),
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
fun ShimmerNewsBigItemPreview() {
    NewsAppTheme {
        Surface {
            ShimmerNewsBigItem()
        }
    }
}

