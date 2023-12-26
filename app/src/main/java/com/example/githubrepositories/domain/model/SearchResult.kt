package com.example.githubrepositories.domain.model

data class SearchResult(
    val incomplete_results: Boolean,
    val items: List<SearchRepoItem>,
    val total_count: Int
)