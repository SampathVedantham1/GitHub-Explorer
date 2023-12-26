package com.example.githubrepositories.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubrepositories.domain.model.RepoItem

/**
 * Data Access Object (DAO) interface for the Repositories table, providing methods to interact with
 * the local database and perform operations on repository data.
 */
@Dao
interface RepoItemDAO {
    /**
     * Retrieves all repositories from the Repositories table in the local database.
     *
     * @return A list of [RepoItem] objects representing the repositories.
     */
    @Query("SELECT * FROM Repositories")
    suspend fun getRepositories(): List<RepoItem>

    /**
     * Inserts or replaces a list of repositories into the Repositories table in the local database.
     * If a repository with the same primary key already exists, it will be replaced.
     *
     * @param repos The list of [RepoItem] objects to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repos: List<RepoItem>)
}