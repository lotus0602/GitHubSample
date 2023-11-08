package com.n.githubsample.domain.model

import com.n.githubsample.domain.FailResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceCode(
    @SerialName("device_code")
    val deviceCode: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    val interval: Int,
    @SerialName("user_code")
    val userCode: String,
    @SerialName("verification_uri")
    val verificationUri: String
) : FailResponse()