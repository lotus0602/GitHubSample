package com.n.githubsample.data.repository

import com.n.githubsample.data.api.AuthService
import com.n.githubsample.domain.model.AccessToken
import com.n.githubsample.domain.model.DeviceCode
import com.n.githubsample.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService
) : AuthRepository {

    override suspend fun getDeviceCode(clientID: String, scope: String): DeviceCode {
        val body = HashMap<String, Any>()
            .apply {
                put("client_id", clientID)
                put("scope", scope)
            }
        return service.getDeviceCode(body)
    }

    override suspend fun getAccessToken(
        clientID: String,
        deviceCode: String,
        grantType: String
    ): AccessToken {
        val body = HashMap<String, Any>()
            .apply {
                put("client_id", clientID)
                put("device_code", deviceCode)
                put("grant_type", grantType)
            }
        return service.getAccessToken(body)
    }
}