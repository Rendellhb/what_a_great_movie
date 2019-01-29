package com.scissorboy.scissorboytest.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") @Nullable val id: String?,
    @SerializedName("username") val username: String,
    @SerializedName("created_at") @Nullable val created_at: String?,
    @SerializedName("updated_at") @Nullable val updated_at: String?,
    @SerializedName("url") @Nullable val url: String?
)
