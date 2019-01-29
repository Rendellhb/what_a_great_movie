package com.scissorboy.scissorboytest.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val movieId: String,
    @SerializedName("thumbnail") val movieThumbUrl: String = "",
    @SerializedName("name") val movieName: String,
    @SerializedName("featured") val movieFeatured: Boolean,
    @SerializedName("gentres") val genres: List<Genres>,
    @SerializedName("year") val year: Int,
    @SerializedName("director" ) val director: String,
    @SerializedName("description") val description: String,
    @SerializedName("main_star") val mainStar: String,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("updated_at") val updatedAtAt: Long
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.createTypedArrayList(Genres),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong()
    )

    fun getYearString(): String {
        return year.toString()
    }

    override fun toString(): String {
        return movieName
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(movieId)
        parcel.writeString(movieThumbUrl)
        parcel.writeString(movieName)
        parcel.writeByte(if (movieFeatured) 1 else 0)
        parcel.writeTypedList(genres)
        parcel.writeInt(year)
        parcel.writeString(director)
        parcel.writeString(description)
        parcel.writeString(mainStar)
        parcel.writeLong(createdAt)
        parcel.writeLong(updatedAtAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}