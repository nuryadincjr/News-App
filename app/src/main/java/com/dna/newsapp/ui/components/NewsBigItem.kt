package com.dna.newsapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dna.newsapp.data.repository.news.impl.article
import com.dna.newsapp.model.Articles
import com.dna.newsapp.ui.theme.NewsAppTheme
import com.dna.newsapp.ui.utils.ImageHandler
import com.dna.newsapp.ui.utils.formatPublishedAt

@Composable
fun NewsBigItem(
    article: Articles,
    onTapItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = MaterialTheme.typography
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = modifier
                .clickable { article.url?.let { onTapItem(it) } }
                .fillMaxWidth()

        ) {
            ImageHandler(
                url = article.urlToImage.toString(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = article.author.toString(),
                    style = typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
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
fun NewsBigItemPreview() {
    NewsAppTheme {
        Surface {
            NewsBigItem(onTapItem = {}, article = article!!)
        }
    }
}

