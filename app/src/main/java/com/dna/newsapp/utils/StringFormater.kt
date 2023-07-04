package com.dna.newsapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatPublishedAt(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val outputFormat = SimpleDateFormat("E, d MMMM HH.mm", Locale("id"))

    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(it) }
}