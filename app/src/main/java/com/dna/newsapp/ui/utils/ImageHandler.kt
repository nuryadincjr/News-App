package com.dna.newsapp.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.dna.newsapp.R


@Composable
fun ImageHandler(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        placeholder = painterResource(id = R.drawable.ic_image_load),
        error = painterResource(id = R.drawable.ic_image_broken),
        modifier = modifier,
        contentScale = contentScale
    )
}
