package com.example.githubrepositories.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Owner(
    val login: String,
): Parcelable {

    /**
     * Constructor for creating an [Owner] from a [Parcel].
     *
     * @param parcel The [Parcel] from which to read the [Owner] data.
     */
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "the user didnt entered the User name"
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    /**
     * Writes the object's data to the provided [Parcel].
     *
     * @param parcel The [Parcel] to which the data should be written.
     * @param flags Additional flags about how the object should be written.
     */
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(login)
    }

    /**
     * Companion object providing a [Parcelable.Creator] for creating instances of [Owner]
     * from a [Parcel].
     */
    companion object CREATOR : Parcelable.Creator<Owner> {
        override fun createFromParcel(parcel: Parcel): Owner {
            return Owner(parcel)
        }

        override fun newArray(size: Int): Array<Owner?> {
            return arrayOfNulls(size)
        }
    }
}