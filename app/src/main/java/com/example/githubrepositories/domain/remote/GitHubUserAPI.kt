package com.example.githubrepositories.domain.remote


import com.example.githubrepositories.domain.model.RepoItem
import com.example.githubrepositories.domain.model.SearchResult
import com.example.githubrepositories.domain.model.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface defining the GitHub User API endpoints for retrieving user and repository data.
 */
interface GitHubUserAPI {

    /**
     * Retrieves information about a specific user.
     *
     * @return An instance of [UserInfo] containing user information.
     */
    @GET("users/rps2579")
    suspend fun getUserInfo(): UserInfo

    /**
     * Retrieves a list of repositories for a specific user.
     *
     * @return A list of [RepoItem] objects representing the user's repositories.
     */
    @GET("users/rps2579/repos")
    suspend fun getRepos(): List<RepoItem>

    /**
     * Searches for repositories on GitHub based on the provided query.
     *
     * @param query The search query.
     * @return A [Call] containing the search results as a [SearchResult] object.
     */
    @GET("search/repositories")
    fun getSearchRepositories(
        @Query("q") query: String,
    ): Call<SearchResult>
}