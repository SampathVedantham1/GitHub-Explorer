package com.example.githubrepositories.domain.model

/**
 * Enum class representing different source types for retrieving data.
 *
 * @property LIST_OF_USER_REPOS Enum representing the source type as list of user repositories.
 * @property SEARCH_REPOS Enum representing the source type as searching repositories.
 */
enum class SourceType {
    LIST_OF_USER_REPOS,
    SEARCH_REPOS
}