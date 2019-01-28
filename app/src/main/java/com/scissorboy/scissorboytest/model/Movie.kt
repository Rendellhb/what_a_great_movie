package com.scissorboy.scissorboytest.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val movieId: String,
    @SerializedName("posterUrl") val movieThumbUrl: String = "",
    @SerializedName("title") val movieName: String,
    @SerializedName("featured") val movieFeatured: Boolean,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("year") val year: Int,
    @SerializedName("director" ) val director: String,
    @SerializedName("plot") val description: String,
    @SerializedName("actors") val actors: String
) {
    override fun toString(): String {
        return movieName
    }
}