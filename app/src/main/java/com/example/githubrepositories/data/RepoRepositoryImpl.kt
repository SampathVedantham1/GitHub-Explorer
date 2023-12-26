package com.example.githubrepositories.data

import android.util.Log
import com.example.githubrepositories.domain.model.RepoItem
import com.example.githubrepositories.domain.model.RepoRepository
import com.example.githubrepositories.domain.remote.GitHubUserAPI

/**
 * Implementation of the [RepoRepository] interface that provides methods to retrieve repository data.
 *
 * This class handles the logic for fetching repositories, first checking the local database
 * and then calling the GitHub API if no cached data is available.
 *
 * @param apiService An instance of [GitHubUserAPI] used to make API calls for repository data.
 * @param repoItemDao An instance of [RepoItemDAO] used to interact with the local database for repository data.
 */
class RepoRepositoryImpl : RepoRepository {


    /**
     * Retrieves a list of repositories. If there are cached repositories in the local database,
     * it returns the cached data. Otherwise, it fetches the data from the GitHub API and
     * updates the local database with the new data.
     *
     * @param apiService An instance of [GitHubUserAPI] for making API calls.
     * @param repoItemDao An instance of [RepoItemDAO] for local database operations.
     * @return A list of [RepoItem] objects representing the repositories.
     */
    override suspend fun getRepos(
        apiService: GitHubUserAPI,
        repoItemDao: RepoItemDAO,
    ): List<RepoItem> {
        // Retrieve cached repositories from the local database
        val localRepos = repoItemDao.getRepositories()

        Log.d("Application Debug", "getRepos-RepoRepositoryImpl: The local list is $localRepos")

        return if (localRepos.isEmpty()) {
            // If no local data is available, fetch repositories from the GitHub API
            val remoteRepos = apiService.getRepos()

            // Extract the user name from API response and update each repository's ownerName
            val userName = apiService.getUserInfo().name
            remoteRepos.map { it.ownerName = userName }

            // Insert the fetched repositories into the local database
            repoItemDao.insertRepositories(remoteRepos)

            Log.d(
                "Application Debug",
                "getRepos-RepoRepositoryImpl: The list fetched from API and cached values $remoteRepos"
            )
            remoteRepos
        } else {
            // If local data is available, log and return the cached repositories
            Log.d("Application Debug", "getRepos-RepoRepositoryImpl: The cached values are $localRepos")
            localRepos
        }
    }
}