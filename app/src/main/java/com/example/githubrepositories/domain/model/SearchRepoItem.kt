package com.example.githubrepositories.domain.model

import android.os.Parcel
import android.os.Parcelable

data class SearchRepoItem(
    val description: String,
    val language: String,
    val name: String,
    val owner: Owner,
    val stargazers_count: Int,
) : Parcelable {
    /**
     * Constructor for creating a [SearchRepoItem] from a [Parcel].
     *
     * @param parcel The [Parcel] from which to read the [SearchRepoItem] data.
     */
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "The user has not provided a description for this repository.",
        parcel.readString() ?: "The user has not updated the language.",
        parcel.readString() ?: "The user has not updated their name.",
        parcel.readParcelable(Owner::class.java.classLoader)
            ?: Owner("The user name is not available."),
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    /**
     * Writes the object's data to the provided [Parcel].
     *
     * @param parcel The [Parcel] to which the data should be written.
     * @param flags Additional flags about how the object should be written.
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(language)
        parcel.writeString(name)
        parcel.writeParcelable(owner, flags)
        parcel.writeInt(stargazers_count)
    }

    /**
     * Companion object providing a [Parcelable.Creator] for creating instances of [SearchRepoItem]
     * from a [Parcel].
     */
    companion object CREATOR : Parcelable.Creator<SearchRepoItem> {
        override fun createFromParcel(parcel: Parcel): SearchRepoItem {
            return SearchRepoItem(parcel)
        }

        override fun newArray(size: Int): Array<SearchRepoItem?> {
            return arrayOfNulls(size)
        }
    }

}