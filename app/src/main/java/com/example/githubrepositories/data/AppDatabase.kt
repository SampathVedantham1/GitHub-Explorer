package com.example.githubrepositories.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubrepositories.domain.model.RepoItem

/**
 * Room Database class for the application, extending RoomDatabase.
 *
 * This class defines the entities in the database and specifies the version of the database.
 * It also provides an abstract method to obtain the Data Access Object (DAO) for repository operations.
 *
 * @property entities An array of entity classes that represent tables in the database.
 * @property version The version number of the database. Increment this number when making changes
 * to the database schema to trigger a migration.
 */
@Database(entities = [RepoItem::class], version = 7)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Provides an instance of the [RepoItemDAO] to perform repository-related database operations.
     *
     * @return An instance of [RepoItemDAO].
     */
    abstract fun repoItemDao(): RepoItemDAO
}