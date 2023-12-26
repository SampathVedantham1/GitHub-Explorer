package com.example.githubrepositories.domain.model

import com.example.githubrepositories.data.RepoItemDAO
import com.example.githubrepositories.domain.remote.GitHubUserAPI

/**
 * Interface defined for repository-related operations.
 */
interface RepoRepository {
    suspend fun getRepos(apiService: GitHubUserAPI, repoItemDao: RepoItemDAO): List<RepoItem>
}