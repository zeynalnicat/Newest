package com.example.newest.domain.models

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)