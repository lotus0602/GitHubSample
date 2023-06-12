package com.n.githubsample.domain.model

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("access_token")
    val accessToken: String,
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String,

    val error: String = "",
    @SerializedName("error_description")
    val errorDescription: String = "",
    @SerializedName("error_uri")
    val errorUri: String = ""
)