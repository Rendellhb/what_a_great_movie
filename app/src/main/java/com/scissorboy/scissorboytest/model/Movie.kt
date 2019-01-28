package com.scissorboy.scissorboytest.model

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
    @SerializedName("main_star") val actors: String,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("updated_at") val updatedAtAt: Long
) {
    override fun toString(): String {
        return movieName
    }
}