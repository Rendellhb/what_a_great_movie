package com.scissorboy.scissorboytest.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("url") val url: String
)
