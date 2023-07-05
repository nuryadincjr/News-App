package com.dna.newsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SearchHandler(
    modifier: Modifier,
    errorMessage: String,
    imageVector: ImageVector,
) {
    Box(
        modifier = modifier
            .fillMaxSize(1f)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Text(text = errorMessage, textAlign = TextAlign.Center)
        }
    }
}
