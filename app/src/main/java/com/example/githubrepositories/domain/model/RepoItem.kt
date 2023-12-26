package com.example.githubrepositories.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing a repository item in the local database, annotated with Room's
 * `@Entity` annotation to define the table name.
 *
 * @property id An auto-generated primary key for the repository item.
 * @property ownerName A string representing the owner's name of the repository.
 * @property name A string representing the name of the repository.
 * @property description A string representing the description of the repository.
 * @property language A string representing the programming language of the repository.
 * @property stargazersCount An integer representing the number of stars (stargazers) the repository has.
 */
@Entity(tableName = "Repositories")
data class RepoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var ownerName: String?,
    var name: String?,
    val description: String?,
    val language: String?,
    val stargazersCount: Int,
) : Parcelable {

    /**
     * Constructor for creating a [RepoItem] from a [Parcel].
     *
     * @param parcel The [Parcel] from which to read the [RepoItem] data.
     */
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        ownerName = parcel.readString() ?: "The user name is not available.",
        name = parcel.readString() ?: "The user has not updated their name.",
        description = parcel.readString()
            ?: "The user has not provided a description for this repository.",
        language = parcel.readString() ?: "The user has not updated the language.",
        stargazersCount = parcel.readInt()
    )

    /**
     * Writes the object's data to the provided [Parcel].
     *
     * @param parcel The [Parcel] to which the data should be written.
     * @param flags Additional flags about how the object should be written.
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(ownerName)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(language)
        parcel.writeInt(stargazersCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    /**
     * Companion object providing a [Parcelable.Creator] for creating instances of [RepoItem]
     * from a [Parcel].
     */
    companion object CREATOR : Parcelable.Creator<RepoItem> {
        override fun createFromParcel(parcel: Parcel): RepoItem {
            return RepoItem(parcel)
        }

        override fun newArray(size: Int): Array<RepoItem?> {
            return arrayOfNulls(size)
        }
    }
}