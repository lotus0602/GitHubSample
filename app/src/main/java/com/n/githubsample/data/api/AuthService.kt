package com.n.githubsample.data.api

import com.n.githubsample.domain.model.AccessToken
import com.n.githubsample.domain.model.DeviceCode
import retrofit2.http.*

interface AuthService {

    @Headers("Accept: application/json")
    @POST("login/device/code")
    suspend fun getDeviceCode(@Body body: HashMap<String, Any>): DeviceCode

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    suspend fun getAccessToken(@Body body: HashMap<String, Any>): AccessToken
}