package com.n.githubsample.domain.repository

import com.n.githubsample.domain.model.AccessToken
import com.n.githubsample.domain.model.DeviceCode

interface AuthRepository {

    suspend fun getDeviceCode(
        clientID: String,
        scope: String
    ): DeviceCode

    suspend fun getAccessToken(
        clientID: String,
        deviceCode: String,
        grantType: String
    ): AccessToken
}