package com.dna.newsapp.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.formatPublishedAt(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date: Date = inputFormat.parse(this) as Date

    val outputFormat = SimpleDateFormat("EEE, d MMMM yy.HH", Locale.getDefault())
    return outputFormat.format(date)
}