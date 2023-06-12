package com.n.githubsample.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    @SerialName("access_token")
    val accessToken: String,
    val scope: String,
    @SerialName("token_type")
    val tokenType: String,

    val error: String = "",
    @SerialName("error_description")
    val errorDescription: String = "",
    @SerialName("error_uri")
    val errorUri: String = ""
)