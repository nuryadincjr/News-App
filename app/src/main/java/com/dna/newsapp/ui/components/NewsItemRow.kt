package com.dna.newsapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dna.newsapp.R
import com.dna.newsapp.data.local.entity.NewsEntity
import com.dna.newsapp.ui.theme.NewsAppTheme
import com.dna.newsapp.ui.utils.ImageHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsItemRow(
    article: NewsEntity,
    onTapItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { article.url?.let { onTapItem("View") } },
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .height(130.dp)
            .fillMaxWidth()
    ) {
        val typography = MaterialTheme.typography

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ImageHandler(
                url = article.urlToImage.toString(),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .fillMaxWidth(),
            )
            Row(Modifier.fillMaxSize(1f)) {
                Text(
                    text = article.title,
                    style = typography.titleMedium,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                )
                IconButton(
                    onClick = { onTapItem("Delete") },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(R.string.remove_item)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NewsItemRowPreview() {
    val newsEntity = NewsEntity("1", "https://", "Title", "")
    NewsAppTheme {
        Surface {
            NewsItemRow(onTapItem = {}, article = newsEntity)
        }
    }
}

