package com.dna.newsapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dna.newsapp.data.repository.news.impl.article
import com.dna.newsapp.model.Articles
import com.dna.newsapp.ui.theme.NewsAppTheme
import com.dna.newsapp.ui.utils.ImageHandler
import com.dna.newsapp.ui.utils.formatPublishedAt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsItem(
    article: Articles,
    onTapItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { article.url?.let { onTapItem(it) } },
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .size(280.dp, 240.dp)
    ) {
        val typography = MaterialTheme.typography

        Column {
            ImageHandler(
                url = article.urlToImage.toString(),
                contentDescription = null,
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth(),
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title,
                    style = typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = article.author.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.titleSmall
                )
                Text(
                    text = article.publishedAt.formatPublishedAt(),
                    style = typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
fun NewsItemPreview() {
    NewsAppTheme {
        Surface {
            NewsItem(onTapItem = {}, article = article!!)
        }
    }
}

