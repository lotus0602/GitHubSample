package com.n.githubsample.domain.model

import com.n.githubsample.domain.FailResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    @SerialName("access_token")
    val accessToken: String = "",
    val scope: String = "",
    @SerialName("token_type")
    val tokenType: String = ""
) : FailResponse()